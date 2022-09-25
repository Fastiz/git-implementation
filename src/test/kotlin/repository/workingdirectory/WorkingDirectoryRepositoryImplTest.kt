package repository.workingdirectory

import dao.files.FileDao
import directory.DataProvider.buildGit
import directory.DataProvider.buildObjects
import directory.DataProvider.buildRoot
import directory.Git
import directory.Objects
import directory.Root
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
    private val root = buildRoot()
    private val git = buildGit()
    private val objects = buildObjects()
    private lateinit var fileDao: FileDao
    private lateinit var workingDirectoryRepositoryImpl: WorkingDirectoryRepositoryImpl

    @Before
    fun before() {
        fileDao = mockk()
        workingDirectoryRepositoryImpl = WorkingDirectoryRepositoryImpl(
            root = root,
            git = git,
            objects = objects,
            fileDao = fileDao,
        )
    }

    @Test
    fun `clear - calls file dao`() {
        every { fileDao.removeAllExcluding(any(), any()) } just runs

        workingDirectoryRepositoryImpl.clear()

        verify {
            fileDao.removeAllExcluding(
                directory = root.path,
                excluding = listOf(git.path)
            )
        }
    }

    @Test
    fun `bringBlob - calls file dao`() {
        every { fileDao.copyFile(any(), any()) } just runs

        val fileBlob = buildFileBlob(id = FileBlobId.from("id"), path = "path")
        workingDirectoryRepositoryImpl.bringBlob(fileBlob)

        verify { fileDao.copyFile(origin = objects.extend(fileBlob.id.value), target = root.extend(fileBlob.path)) }
    }
}
