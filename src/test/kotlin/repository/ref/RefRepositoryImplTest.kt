package repository.ref

import dao.files.FileDao
import directory.DataProvider.buildRefsHeads
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import model.CommitId
import org.junit.Before
import org.junit.Test

import org.junit.jupiter.api.Assertions.*

class RefRepositoryImplTest {
    private val refsHeads = buildRefsHeads()
    private lateinit var fileDao: FileDao

    private lateinit var refRepository: RefRepository

    @Before
    fun beforeEach() {
        fileDao = mockk()

        refRepository = RefRepositoryImpl(
            refsHeads = refsHeads,
            fileDao = fileDao
        )
    }

    @Test
    fun `create - calls file dao`() {
        every { fileDao.writeFile(any(), any()) } just runs

        refRepository.create("ref-name", CommitId.from("commit-id"))

        verify { fileDao.writeFile(eq(refsHeads.extend("ref-name")), any()) }
    }
}