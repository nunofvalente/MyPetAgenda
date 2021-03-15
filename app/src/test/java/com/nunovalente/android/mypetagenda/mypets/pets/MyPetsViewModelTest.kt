package com.nunovalente.android.mypetagenda.mypets.pets

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nunovalente.android.mypetagenda.data.local.FakeRepository
import com.nunovalente.android.mypetagenda.getOrAwaitValue
import com.nunovalente.android.mypetagenda.models.Pet
import com.nunovalente.android.mypetagenda.ui.mypets.pets.MyPetsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MyPetsViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    private lateinit var petRepository: FakeRepository

    private lateinit var petViewModel: MyPetsViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() = runBlocking {
        petRepository = FakeRepository()
        val pet1 = Pet("id1", "name1", "birthday1", "type1", "breed1", "weight1", "path1")
        val pet2 = Pet("id2", "name2", "birthday2", "type2", "breed2", "weight2", "path2")
        val pet3 = Pet("id3", "name3", "birthday3", "type3", "breed3", "weight3", "path3")
        petRepository.insertPet(pet1)
        petRepository.insertPet(pet2)
        petRepository.insertPet(pet3)
        petViewModel = MyPetsViewModel(petRepository)
    }

    @Test
    fun loadPets_checkListIsReturned() {
        petViewModel.loadPets()
        val pets = petViewModel.petList.getOrAwaitValue()

        assertThat(pets?.size, `is`(3))
        assertThat(pets, `is`(petRepository.getAllPets().value))
    }
}