package service.commit.step

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import repository.commit.CommitRepository
import kotlin.test.assertEquals

class CreateCommitTest {
    private lateinit var commitRepository: CommitRepository
    private lateinit var createCommit: CreateCommit

    @Before
    fun before() {
        commitRepository = mockk()
        createCommit = CreateCommit(commitRepository)
    }

    @Test
    fun `calls commit repository and returns correctly`() {
        val input = OutputCreateNewTree(
            treeId = "tree-id",
            parentId = "parent-id"
        )

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