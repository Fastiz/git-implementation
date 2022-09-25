package service.branch

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import model.CommitId
import org.junit.Before
import org.junit.Test
import repository.head.HeadRepository
import repository.ref.RefRepository


internal class BranchServiceImplTest {
    private lateinit var headRepository: HeadRepository
    private lateinit var refRepository: RefRepository

    private lateinit var branchServiceImpl: BranchServiceImpl

    @Before
    fun before() {
        headRepository = mockk()
        refRepository = mockk()

        branchServiceImpl = BranchServiceImpl(
            headRepository = headRepository,
            refRepository = refRepository
        )
    }

    @Test
    fun `calls ref repository`() {
        val commitId = CommitId.from("current-commit-id")

        every { headRepository.getHead() } returns commitId
        every { refRepository.set(any(), any()) } just runs

        branchServiceImpl.run("branch-name")

        verify { refRepository.set("branch-name", commitId) }
    }
}