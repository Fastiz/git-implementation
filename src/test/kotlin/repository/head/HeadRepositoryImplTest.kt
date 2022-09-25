package repository.head

import dao.files.FileDao
import dao.files.Reader
import directory.DataProvider.buildHead
import io.mockk.every
import io.mockk.mockk
import model.CommitId
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class HeadRepositoryImplTest {
    private val head = buildHead()
    private lateinit var fileDao: FileDao
    private lateinit var headRepositoryImpl: HeadRepositoryImpl

    @Before
    fun before() {
        fileDao = mockk()
        headRepositoryImpl = HeadRepositoryImpl(head, fileDao)
    }

    @Test
    fun `getHead - retrieves the ref commit hash`() {
        val expectedCommitId = CommitId.from("commit-id")

        every {
            fileDao.readFile(
                eq(head.path),
                any<Reader.() -> String?>()
            )
        } returns "ref: ${expectedCommitId.value}"

        val result = headRepositoryImpl.getHead()

        assertEquals(expectedCommitId, result)
    }

    @Test
    fun `getHead - if stored ref is empty then return null`() {
        every { fileDao.readFile(eq(head.path), any<Reader.() -> String?>()) } returns null

        val result = headRepositoryImpl.getHead()

        assertNull(result)
    }
}
