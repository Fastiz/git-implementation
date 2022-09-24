package service.commit.step

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logger.TestLogger
import model.CommitDataProvider.buildCommit
import org.junit.Before
import org.junit.Test
import repository.commit.CommitRepository
import repository.head.HeadRepository
import kotlin.test.assertEquals

class CreateCommitTest {
    private lateinit var commitRepository: CommitRepository
    private lateinit var headRepository: HeadRepository
    private var logger = TestLogger()

    private lateinit var createCommit: CreateCommit

    @Before
    fun before() {
        commitRepository = mockk()
        headRepository = mockk()
        createCommit = CreateCommit(headRepository, commitRepository, logger)
    }

    @Test
    fun `calls commit repository and returns correctly`() {
        val input = OutputCreateNewTree(
            treeId = "tree-id",
        )

        every { headRepository.getHead() } returns "parent-id"
        every { commitRepository.get(any()) } returns buildCommit(id = "parent-id")
        every { commitRepository.create(any(), any(), any()) } returns "commit-id"

        val result = createCommit.execute(input)

        assertEquals("commit-id", result.commitId)
        verify {
            commitRepository.create(
                treeId = "tree-id",
                parentId = "parent-id",
                message = ""
            )
        }
    }
}