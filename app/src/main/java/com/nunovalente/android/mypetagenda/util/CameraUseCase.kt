package com.nunovalente.android.mypetagenda.util

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.util.Size
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.graphics.rotationMatrix
import androidx.core.net.toFile
import androidx.lifecycle.LifecycleOwner
import com.nunovalente.android.mypetagenda.R
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CameraUseCase @Inject constructor(private val activity: AppCompatActivity) {

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    private lateinit var camera: Camera
    private lateinit var cameraControl: CameraControl

    private var imageCapture: ImageCapture? = null
    private var outputDirectory: File = getOutputDirectory()
    private var flashMode: Int = ImageCapture.FLASH_MODE_OFF
    private lateinit var cameraSelector: CameraSelector
    private var hasFrontCamera: Boolean = false
    private var hasFlash: Boolean = false


    private var lensFacing = CameraSelector.DEFAULT_BACK_CAMERA
    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.UK
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(activity),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Timber.e("Photo capture failed: ${exc.message}")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)

                    //Implicit broadcasts will be ignored for devices running API level >= 24
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        activity.sendBroadcast(
                            Intent(android.hardware.Camera.ACTION_NEW_PICTURE, savedUri)
                        )
                    }

                    val mimeType = MimeTypeMap.getSingleton()
                        .getMimeTypeFromExtension(savedUri.toFile().extension)
                    MediaScannerConnection.scanFile(
                        activity,
                        arrayOf(savedUri.toFile().absolutePath),
                        arrayOf(mimeType)
                    ) { _, uri ->
                        Timber.d("Image capture scanned into media store: $uri")
                    }

                    val msg = "Image Saved"
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
                    Timber.d(msg)
                }
            })
    }

    fun startCamera(
        surfaceProvider: Preview.SurfaceProvider,
        lifecycleOwner: LifecycleOwner,
        viewFinder: PreviewView
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider = cameraProviderFuture.get()
            hasFrontCamera = cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setTargetResolution(Size(600, 800))
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .setFlashMode(flashMode)
                .build()

            // Select back camera as a default
            cameraSelector = lensFacing

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )

                hasFlash = camera.cameraInfo.hasFlashUnit()

                cameraControl = camera.cameraControl

                setupZoomAndTapToFocus(viewFinder)

            } catch (exc: Exception) {
                Timber.e("Use case binding failed")
            }
        }, ContextCompat.getMainExecutor(activity))
    }

    private fun getOutputDirectory(): File {
        val mediaDir = activity.externalMediaDirs.firstOrNull()?.let {
            File(it, activity.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else activity.filesDir
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupZoomAndTapToFocus(viewFinder: PreviewView) {
        val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val currentZoomRatio: Float = camera.cameraInfo.zoomState.value?.zoomRatio ?: 1F
                val delta = detector.scaleFactor
                cameraControl.setZoomRatio(currentZoomRatio * delta)
                return true
            }
        }

        val scaleGestureDetector = ScaleGestureDetector(viewFinder.context, listener)

        viewFinder.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            if (event.action == MotionEvent.ACTION_DOWN) {
                val factory = viewFinder.meteringPointFactory
                val point = factory.createPoint(event.x, event.y)
                val action = FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
                    .setAutoCancelDuration(5, TimeUnit.SECONDS)
                    .build()
                cameraControl.startFocusAndMetering(action)
            }
            true
        }
    }

    fun toggleFlashOnOff(imageView: ImageView) {
        imageView.visibility = View.VISIBLE

        if (hasFlash) {
            when (flashMode) {
                ImageCapture.FLASH_MODE_OFF -> {
                    flashMode = ImageCapture.FLASH_MODE_ON
                    imageCapture?.flashMode = flashMode
                    imageView.setImageResource(R.drawable.ic_flash_on)
                }
                ImageCapture.FLASH_MODE_ON -> {
                    flashMode = ImageCapture.FLASH_MODE_OFF
                    imageCapture?.flashMode = flashMode
                    imageView.setImageResource(R.drawable.ic__flash_off)
                }
            }
        } else {
            imageView.visibility = View.GONE
        }
    }

    fun flipCamera(imageView: ImageView) {
        if (hasFrontCamera) {
            when (lensFacing) {
                CameraSelector.DEFAULT_BACK_CAMERA -> {
                    lensFacing =  CameraSelector.DEFAULT_FRONT_CAMERA
                    hasFlash = false
                }
                CameraSelector.DEFAULT_FRONT_CAMERA -> {
                    lensFacing =  CameraSelector.DEFAULT_BACK_CAMERA
                    hasFlash = true
                }
            }
        } else {
            imageView.visibility = View.GONE
        }
    }

    fun shutdownExecutor() {
        cameraExecutor.shutdown()
    }

    fun hasFlash(): Boolean {
        return hasFlash
    }
}