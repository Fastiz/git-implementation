package service.commit.step

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.CommitDataProvider.buildCommit
import model.FileBlobDataProvider.buildFileBlob
import model.TreeDataProvider.buildTree
import org.junit.Before
import org.junit.Test
import repository.commit.CommitRepository
import repository.head.HeadRepository
import repository.tree.TreeRepository
import kotlin.test.assertEquals

class CreateNewTreeTest {
    private lateinit var treeRepository: TreeRepository
    private lateinit var commitRepository: CommitRepository
    private lateinit var headRepository: HeadRepository
    private lateinit var createNewTree: CreateNewTree

    @Before
    fun before() {
        treeRepository = mockk()
        commitRepository = mockk()
        headRepository = mockk()
        createNewTree = CreateNewTree(
            commitRepository = commitRepository,
            headRepository = headRepository,
            treeRepository = treeRepository
        )
    }

    @Test
    fun `merges content from trees and calls tree repository`() {
        val currentTreeFileBlob = listOf(
            buildFileBlob(path = "path-1"),
            buildFileBlob(path = "path-2", id = "old-id-2"),
        )

        val newFileBlob = listOf(
            buildFileBlob(path = "path-2", id = "new-id-2"),
            buildFileBlob(path = "path-3"),
        )

        val input = CreateFileBlobsIfNotExistOutput(
            fileBlobList = newFileBlob,
        )

        val mergedFileBlob = listOf(
            buildFileBlob(path = "path-1"),
            buildFileBlob(path = "path-2", id = "new-id-2"),
            buildFileBlob(path = "path-3"),
        )

        every { headRepository.getHead() } returns "commit-id"
        every { commitRepository.get(any()) } returns buildCommit()
        every { treeRepository.get(any()) } returns buildTree(fileBlobList = currentTreeFileBlob)
        every { treeRepository.create(any()) } returns "tree-id"

        val result = createNewTree.execute(input)

        assertEquals("tree-id", result.treeId)
        verify { treeRepository.create(mergedFileBlob) }
    }
}