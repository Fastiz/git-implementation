package repository.blob

import dao.objects.ObjectsDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.FileBlobId
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FileBlobRepositoryImplTest {
    private lateinit var objectsDao: ObjectsDao
    private lateinit var fileBlobRepositoryImpl: FileBlobRepositoryImpl

    @Before
    fun before() {
        objectsDao = mockk()
        fileBlobRepositoryImpl = FileBlobRepositoryImpl(
            objectsDao = objectsDao
        )
    }

    @Test
    fun `createIfNotExists - calls objectsDao to create an object from the read string`() {
        every { objectsDao.createFromPath(any()) } returns "object-id"

        val result = fileBlobRepositoryImpl.createIfNotExists("path")

        verify { objectsDao.createFromPath("path") }
        assertEquals(FileBlobId.from("object-id"), result)
    }
}
