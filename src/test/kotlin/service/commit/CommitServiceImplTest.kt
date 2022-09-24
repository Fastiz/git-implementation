package service.commit

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logger.TestLogger
import org.junit.Before
import org.junit.Test
import service.commit.step.CreateCommit
import service.commit.step.CreateCommitOutput
import service.commit.step.CreateFileBlobsIfNotExist
import service.commit.step.CreateFileBlobsIfNotExistInput
import service.commit.step.CreateFileBlobsIfNotExistOutput
import service.commit.step.CreateNewTree
import service.commit.step.MoveHead
import service.commit.step.OutputCreateNewTree

class CommitServiceImplTest {
    private lateinit var createFileBlobsIfNotExist: CreateFileBlobsIfNotExist
    private lateinit var createNewTree: CreateNewTree
    private lateinit var createCommit: CreateCommit
    private lateinit var moveHead: MoveHead
    private val logger = TestLogger()
    private lateinit var commitServiceImpl: CommitServiceImpl

    @Before
    fun before() {
        createFileBlobsIfNotExist = mockk()
        createNewTree = mockk()
        createCommit = mockk()
        moveHead = mockk()
        commitServiceImpl = CommitServiceImpl(
            createFileBlobsIfNotExist = createFileBlobsIfNotExist,
            createNewTree = createNewTree,
            createCommit = createCommit,
            moveHead = moveHead,
            logger = logger
        )
    }

    @Test
    fun `calls all the steps`() {
        val stagedFiles = listOf("sample-file.kt")
        val input = CreateFileBlobsIfNotExistInput(
            stagedFiles = stagedFiles
        )
        val createFileBlobsIfNotExistOutput = CreateFileBlobsIfNotExistOutput(
            fileBlobList = emptyList()
        )
        val createNewTreeOutput = OutputCreateNewTree(
            treeId = "tree-id",
        )
        val createCommitOutput = CreateCommitOutput(
            commitId = "commit-id"
        )

        every { createFileBlobsIfNotExist.execute(any()) } returns createFileBlobsIfNotExistOutput
        every { createNewTree.execute(any()) } returns createNewTreeOutput
        every { createCommit.execute(any()) } returns createCommitOutput
        every { moveHead.execute(any()) } returns "commit-id"

        commitServiceImpl.run(stagedFiles)

        verify { createFileBlobsIfNotExist.execute(input) }
        verify { createNewTree.execute(createFileBlobsIfNotExistOutput) }
        verify { createCommit.execute(createNewTreeOutput) }
        verify { moveHead.execute(createCommitOutput) }
    }
}