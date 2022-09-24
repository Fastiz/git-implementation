package service.commit.step

import io.mockk.every
import io.mockk.mockk
import model.FileBlob
import model.FileTreeEntry
import model.SubtreeTreeEntry
import model.Tree
import model.TreeDataProvider.buildTree
import org.junit.Test

import service.commit.step.GroupFiles.groupFilesByFolder
import service.commit.step.GroupFiles.groupFilesByFolderFromTree
import service.commit.step.GroupFiles.mergeGroupedFiles
import kotlin.test.assertEquals

internal class GroupFilesTest {

    @Test
    fun `groupFilesByFolder - groups them correctly`() {
        val fileBlob1 = FileBlob("/dir1/dir2/file1.jpg", "id-1")
        val fileBlob2 = FileBlob("/dir1/file2.pdf", "id-2")
        val fileBlob3 = FileBlob("/dir1/file3.doc", "id-3")
        val fileBlob4 = FileBlob("/dir1/dir3/file4.png", "id-3")

        val fileBlobs = listOf(fileBlob1, fileBlob2, fileBlob3, fileBlob4)

        val expectedResult = mapOf(
            "/dir1/dir2" to listOf(fileBlob1),
            "/dir1" to listOf(fileBlob2, fileBlob3),
            "/dir1/dir3" to listOf(fileBlob4)
        )

        val result = groupFilesByFolder(fileBlobs)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `groupFilesByFolderFromTree - groups them correctly`() {
        val fileBlob1 = FileBlob("./dir2/file1.jpg", "id-1")
        val fileBlob2 = FileBlob("./file2.pdf", "id-2")
        val fileBlob3 = FileBlob("./file3.doc", "id-3")
        val fileBlob4 = FileBlob("./dir3/file4.png", "id-3")

        val tree1 = buildTree(
            id = "tree-2",
            entries = listOf(
                FileTreeEntry(path = fileBlob1.path, fileBlobId = fileBlob1.id),
            )
        )
        val tree2 = buildTree(
            id = "tree-3",
            entries = listOf(
                FileTreeEntry(path = fileBlob4.path, fileBlobId = fileBlob4.id),
            )
        )
        val tree3 = buildTree(
            id = "tree-1",
            entries = listOf(
                FileTreeEntry(path = fileBlob2.path, fileBlobId = fileBlob2.id),
                FileTreeEntry(path = fileBlob3.path, fileBlobId = fileBlob3.id),
                SubtreeTreeEntry(path = "./dir2", subtreeId = tree1.id),
                SubtreeTreeEntry(path = "./dir3", subtreeId = tree2.id),
            )
        )

        val expectedResult = mapOf(
            "./dir2" to listOf(fileBlob1),
            "." to listOf(fileBlob2, fileBlob3),
            "./dir3" to listOf(fileBlob4)
        )

        val treeProvider = mockk<(treeId: String) -> Tree>()

        every { treeProvider(tree1.id) } returns tree1
        every { treeProvider(tree2.id) } returns tree2

        val result = groupFilesByFolderFromTree(tree3, treeProvider)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `mergeGroupedFiles - groups them correctly`() {
        val baseFileBlob = mapOf(
            "./dir2" to listOf(FileBlob("./dir2/file1.jpg", "base-id-1")),
            "." to listOf(FileBlob("./file2.pdf", "base-id-2")),
        )
        val overrideFileBlob = mapOf(
            "." to listOf(FileBlob("./file2.pdf", "override-id-2"), FileBlob("./file6.doc", "override-id-3")),
            "./dir4" to listOf(FileBlob("./dir4/file5.png", "override-id-4")),
        )

        val expectedResult = mapOf(
            "./dir2" to listOf(FileBlob("./dir2/file1.jpg", "base-id-1")),
            "." to listOf(
                FileBlob("./file2.pdf", "override-id-2"),
                FileBlob("./file6.doc", "override-id-3")
            ),
            "./dir4" to listOf(FileBlob("./dir4/file5.png", "override-id-4")),
        )

        val result = mergeGroupedFiles(base = baseFileBlob, override = overrideFileBlob)

        assertEquals(expectedResult, result)
    }
}