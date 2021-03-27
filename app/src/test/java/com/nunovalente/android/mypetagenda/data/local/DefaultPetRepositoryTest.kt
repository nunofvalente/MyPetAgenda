package com.nunovalente.android.mypetagenda.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nunovalente.android.mypetagenda.data.DefaultPetRepository
import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import com.nunovalente.android.mypetagenda.data.entities.toDomainModel
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
class DefaultPetRepositoryTest {
    private val pet1 =
        DatabasePet(1, "name1", "birthday1", "type1", "breed1", "weight1", "path1")
    private val pet2 =
        DatabasePet(2, "name2", "birthday2", "type2", "breed2", "weight2", "path2")
    private val pet3 =
        DatabasePet(3, "name3", "birthday3", "type3", "breed3", "weight3", "path3")
    private val localPet: List<DatabasePet>? = listOf(pet1, pet2, pet3).sortedBy { it.id }

    private lateinit var petDataSource: FakePetDataSource

    //Class under test
    private lateinit var petDefaultPetRepository: DefaultPetRepository

    @Before
    fun createRepository() {
        petDataSource = FakePetDataSource(localPet?.toMutableList())
        petDefaultPetRepository = DefaultPetRepository(petDataSource)
    }

    @Test
    fun getPets_requestAllPetsFromLocalDataSource() = runBlockingTest {
        val pets = petDefaultPetRepository.getAllPets().getOrAwaitValue()?.map {
            it.toDatabasePet()
        }
        assertThat(pets, IsEqual(localPet))
    }

    @Test
    fun getPetById_checkIfPetReturned() = runBlockingTest {
        val petResult = petDefaultPetRepository.getPet(1) as Result.Success
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
        val petResult = petDefaultPetRepository.getPet(4) as Result.Error

        assertThat(
            petResult.exception.localizedMessage,
            IsEqual(Exception("Pet not found!").localizedMessage)
        )
    }

    @Test
    fun insertPet_returnPet() = runBlocking {
        val petToInsert = Pet(4, "name4", "birthday4", "type4", "breed4", "weight4", "path4")
        petDefaultPetRepository.insertPet(petToInsert)

        val petRetrieved = petDefaultPetRepository.getPet(4) as Result.Success

        assertThat(petRetrieved.data, IsEqual(petToInsert))
    }

    @Test
    fun deletePet_checkIfDeleted() = runBlockingTest {
        val petResult = petDefaultPetRepository.getAllPets().getOrAwaitValue()
        val petResultSize = petResult?.size

        assertThat(petResultSize, IsEqual(3))

        petDefaultPetRepository.deletePet(pet1.toDomainModel())

        val petList = petDefaultPetRepository.getAllPets().getOrAwaitValue()
        val petListSize = petList?.size

        assertThat(petListSize, IsEqual(2))
    }

}