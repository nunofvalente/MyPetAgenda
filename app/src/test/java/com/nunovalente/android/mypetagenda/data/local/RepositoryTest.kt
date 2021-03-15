package com.nunovalente.android.mypetagenda.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nunovalente.android.mypetagenda.data.Repository
import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import com.nunovalente.android.mypetagenda.getOrAwaitValue
import com.nunovalente.android.mypetagenda.models.Pet
import com.nunovalente.android.mypetagenda.models.toDatabasePet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.core.IsEqual
import org.robolectric.annotation.Config
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
@ExperimentalCoroutinesApi
class RepositoryTest {
    private val pet1 =
        DatabasePet("id1", "name1", "birthday1", "type1", "breed1", "weight1", "path1")
    private val pet2 =
        DatabasePet("id2", "name2", "birthday2", "type2", "breed2", "weight2", "path2")
    private val pet3 =
        DatabasePet("id3", "name3", "birthday3", "type3", "breed3", "weight3", "path3")
    private val localPet: List<DatabasePet>? = listOf(pet1, pet2, pet3).sortedBy { it.id }

    private lateinit var petDataSource: FakePetDataSource

    //Class under test
    private lateinit var petRepository: Repository

    @Before
    fun createRepository() {
        petDataSource = FakePetDataSource(localPet?.toMutableList())
        petRepository = Repository(petDataSource)
    }

    @Test
    fun getPets_requestAllPetsFromLocalDataSource() = runBlockingTest {
        val pets = petRepository.getAllPets().getOrAwaitValue()?.map {
            it.toDatabasePet()
        }
        assertThat(pets, IsEqual(localPet))
    }

    @Test
    fun getPetById_checkIfPetReturned() = runBlockingTest {
        val petResult = petRepository.getPet("id1") as Result.Success
        val pet = petResult.data.toDatabasePet()

        assertThat(pet.id, IsEqual(pet1.id))
        assertThat(pet.name, IsEqual(pet1.name))
        assertThat(pet.birthday, IsEqual(pet1.birthday))
        assertThat(pet.type, IsEqual(pet1.type))
        assertThat(pet.breed, IsEqual(pet1.breed))
        assertThat(pet.weight, IsEqual(pet1.weight))
        assertThat(pet.imagePath, IsEqual(pet1.imagePath))
        assertThat(pet, IsEqual(pet1))
    }

    @Test
    fun getPetById_returnError() = runBlockingTest {
        val petResult = petRepository.getPet("id4") as Result.Error

        assertThat(
            petResult.exception.localizedMessage,
            IsEqual(Exception("Pet not found!").localizedMessage)
        )
    }

    @Test
    fun insertPet_returnPet() = runBlocking {
        val petToInsert = Pet("id4", "name4", "birthday4", "type4", "breed4", "weight4", "path4")
        petRepository.insertPet(petToInsert)

        val petRetrieved = petRepository.getPet("id4") as Result.Success

        assertThat(petRetrieved.data, IsEqual(petToInsert))
    }

    @Test
    fun deletePet_checkIfDeleted() = runBlockingTest {
        val petResult = petRepository.getAllPets().getOrAwaitValue()
        val petResultSize = petResult?.size

        assertThat(petResultSize, IsEqual(3))

        petRepository.deletePet(pet1)

        val petList = petRepository.getAllPets().getOrAwaitValue()
        val petListSize = petList?.size

        assertThat(petListSize, IsEqual(2))
    }

}