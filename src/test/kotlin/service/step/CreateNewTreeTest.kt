package service.step

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.FileBlobDataProvider.buildFileBlob
import model.TreeDataProvider.buildTree
import org.junit.Before
import org.junit.Test
import repository.tree.TreeRepository
import service.commit.step.CreateFileBlobsIfNotExistOutput
import service.commit.step.CreateNewTree
import kotlin.test.assertEquals

class CreateNewTreeTest {
    private lateinit var treeRepository: TreeRepository
    private lateinit var createNewTree: CreateNewTree

    @Before
    fun before() {
        treeRepository = mockk()
        createNewTree = CreateNewTree(treeRepository)
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
            currentTree = buildTree(
                fileBlobList = currentTreeFileBlob
            ),
            commitId = "commit-id",
        )

        val mergedFileBlob = listOf(
            buildFileBlob(path = "path-1"),
            buildFileBlob(path = "path-2", id = "new-id-2"),
            buildFileBlob(path = "path-3"),
        )

        every { treeRepository.create(any()) } returns "tree-id"

        val result = createNewTree.execute(input)

        assertEquals("tree-id", result.treeId)
        assertEquals("commit-id", result.parentId)
        verify { treeRepository.create(mergedFileBlob) }
    }
}