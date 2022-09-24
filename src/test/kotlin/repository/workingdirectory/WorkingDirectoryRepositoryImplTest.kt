package repository.workingdirectory

import dao.files.FileDao
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import model.FileBlobDataProvider.buildFileBlob
import model.FileBlobId
import org.junit.Before
import org.junit.Test

class WorkingDirectoryRepositoryImplTest {
    private lateinit var fileDao: FileDao
    private lateinit var workingDirectoryRepositoryImpl: WorkingDirectoryRepositoryImpl

    @Before
    fun before() {
        fileDao = mockk()
        workingDirectoryRepositoryImpl = WorkingDirectoryRepositoryImpl(fileDao)
    }

    @Test
    fun `clear - calls file dao`() {
        every { fileDao.removeAllExcluding(any(), any()) } just runs

        workingDirectoryRepositoryImpl.clear()

        verify {
            fileDao.removeAllExcluding(
                directory = ".",
                excluding = listOf("./.fastiz-git")
            )
        }
    }

    @Test
    fun `bringBlob - calls file dao`() {
        every { fileDao.copyFile(any(), any()) } just runs

        val fileBlob = buildFileBlob(id = FileBlobId.from("id"), path = "path")
        workingDirectoryRepositoryImpl.bringBlob(fileBlob)

        verify { fileDao.copyFile(origin = "./.fastiz-git/objects/id", target = "./path") }
    }
}