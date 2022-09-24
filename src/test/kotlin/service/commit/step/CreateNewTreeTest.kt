package service.commit.step

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import logger.TestLogger
import model.CommitDataProvider.buildCommit
import model.CommitId
import model.FileBlob
import model.FileBlobId
import model.FileTreeEntry
import model.TreeDataProvider.buildTree
import model.TreeId
import model.TreeInput
import org.junit.Before
import org.junit.Test
import repository.commit.CommitRepository
import repository.head.HeadRepository
import repository.tree.TreeRepository
import java.util.UUID
import kotlin.test.assertEquals

class CreateNewTreeTest {
    private lateinit var treeRepository: TreeRepository
    private lateinit var commitRepository: CommitRepository
    private lateinit var headRepository: HeadRepository
    private var logger = TestLogger()

    private lateinit var createNewTree: CreateNewTree

    @Before
    fun before() {
        treeRepository = mockk()
        commitRepository = mockk()
        headRepository = mockk()
        createNewTree = CreateNewTree(
            commitRepository = commitRepository,
            headRepository = headRepository,
            treeRepository = treeRepository,
            logger = logger,
        )
    }

    @Test
    fun `calls tree repository with the correct input`() {
        val fileBlobList = listOf(
            FileBlob(id = FileBlobId.from("id-1"), path = "./file1"),
            FileBlob(id = FileBlobId.from("id-2"), path = "./file2"),
        )
        val currentCommitId = CommitId.from("commit-id")
        val currentCommitTreeId = TreeId.from("current-tree-id")
        val currentCommit = buildCommit(id = currentCommitId, treeId = currentCommitTreeId)
        val currentTree = buildTree(entries = emptyList())
        val resultTreeId = TreeId.from("result-tree-id")

        val input = CreateFileBlobsIfNotExistOutput(fileBlobList = fileBlobList)

        every { headRepository.getHead() } returns currentCommitId
        every { commitRepository.get(currentCommitId) } returns currentCommit
        every { treeRepository.get(currentCommitTreeId) } returns currentTree

        val treeInputSlot = slot<TreeInput>()
        every { treeRepository.create(capture(treeInputSlot)) } returns resultTreeId

        val result = createNewTree.execute(input)

        val expectedTreeInput = TreeInput(
            entries = listOf(
                FileTreeEntry(fileBlobId = FileBlobId.from("id-1"), path = "./file1"),
                FileTreeEntry(fileBlobId = FileBlobId.from("id-2"), path = "./file2"),
            )
        )

        assertEquals(resultTreeId, result.treeId)
        assertEquals(expectedTreeInput, treeInputSlot.captured)
    }
}