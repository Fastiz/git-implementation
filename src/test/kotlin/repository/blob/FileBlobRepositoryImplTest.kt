package repository.blob

import dao.objects.files.FileDao
import dao.objects.files.Reader
import dao.objects.objects.ObjectsDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FileBlobRepositoryImplTest {
    private lateinit var fileDao: FileDao
    private lateinit var objectsDao: ObjectsDao
    private lateinit var fileBlobRepositoryImpl: FileBlobRepositoryImpl

    @Before
    fun before(){
        fileDao = mockk()
        objectsDao = mockk()
        fileBlobRepositoryImpl = FileBlobRepositoryImpl(
            fileDao = fileDao, objectsDao = objectsDao
        )
    }

    @Test
    fun `createIfNotExists - calls objectsDao to create an object from the read string`(){
        every { fileDao.readFile(any(), any<Reader.() -> Sequence<String>>()) } returns listOf("line1", "line2", "").asSequence()
        every { objectsDao.createFromString(any()) } returns "object-id"

        val result = fileBlobRepositoryImpl.createIfNotExists("path")

        verify { objectsDao.createFromString("line1\nline2\n") }
        assertEquals("object-id", result)
    }
}