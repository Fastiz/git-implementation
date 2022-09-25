package service.commit

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logger.TestLogger
import model.CommitId
import model.TreeId
import org.junit.Before
import org.junit.Test
import repository.index.IndexRepository
import service.commit.step.CreateCommit
import service.commit.step.CreateCommitOutput
import service.commit.step.CreateNewTree
import service.commit.step.MoveHead
import service.commit.step.OutputCreateNewTree

class CommitServiceImplTest {
    private lateinit var createNewTree: CreateNewTree
    private lateinit var createCommit: CreateCommit
    private lateinit var moveHead: MoveHead
    private lateinit var indexRepository: IndexRepository
    private val logger = TestLogger()
    private lateinit var commitServiceImpl: CommitServiceImpl

    @Before
    fun before() {
        createNewTree = mockk()
        createCommit = mockk()
        moveHead = mockk()
        indexRepository = mockk()
        commitServiceImpl = CommitServiceImpl(
            indexRepository = indexRepository,
            createNewTree = createNewTree,
            createCommit = createCommit,
            moveHead = moveHead,
            logger = logger
        )
    }

    @Test
    fun `calls all the steps`() {
        val createNewTreeOutput = OutputCreateNewTree(
            treeId = TreeId.from("tree-id"),
        )
        val createCommitOutput = CreateCommitOutput(
            commitId = CommitId.from("commit-id")
        )

        every { createNewTree.execute(any()) } returns createNewTreeOutput
        every { createCommit.execute(any()) } returns createCommitOutput
        every { moveHead.execute(any()) } returns CommitId.from("commit-id")

        commitServiceImpl.run()

        verify { createCommit.execute(createNewTreeOutput) }
        verify { moveHead.execute(createCommitOutput) }
    }
}
