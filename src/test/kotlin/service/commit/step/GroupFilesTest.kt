package service.commit.step

import directory.DataProvider.buildRoot
import io.mockk.every
import io.mockk.mockk
import model.FileBlob
import model.FileBlobId
import model.FileTreeEntry
import model.SubtreeTreeEntry
import model.Tree
import model.TreeDataProvider.buildTree
import model.TreeId
import org.junit.Test
import service.commit.step.GroupFiles.groupFilesByFolder
import service.commit.step.GroupFiles.groupFilesByFolderFromTree
import service.commit.step.GroupFiles.mergeGroupedFiles
import kotlin.test.assertEquals

internal class GroupFilesTest {

    @Test
    fun `groupFilesByFolder - groups them correctly`() {
        val fileBlob1 = FileBlob("/dir1/dir2/file1.jpg", FileBlobId.from("id-1"))
        val fileBlob2 = FileBlob("/dir1/file2.pdf", FileBlobId.from("id-2"))
        val fileBlob3 = FileBlob("/dir1/file3.doc", FileBlobId.from("id-3"))
        val fileBlob4 = FileBlob("/dir1/dir3/file4.png", FileBlobId.from("id-3"))

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
    fun `groupFilesByFolder - groups correctly when there are no directories`() {
        val fileBlob1 = FileBlob("file1.jpg", FileBlobId.from("id-1"))

        val fileBlobs = listOf(fileBlob1)

        val expectedResult = mapOf(
            "" to listOf(fileBlob1)
        )

        val result = groupFilesByFolder(fileBlobs)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `groupFilesByFolderFromTree - groups them correctly`() {
        val root = buildRoot()
        val fileBlob1 = FileBlob("dir2/file1.jpg", FileBlobId.from("id-1"))
        val fileBlob2 = FileBlob("file2.pdf", FileBlobId.from("id-2"))
        val fileBlob3 = FileBlob("file3.doc", FileBlobId.from("id-3"))
        val fileBlob4 = FileBlob("dir3/file4.png", FileBlobId.from("id-3"))
        val fileBlob5 = FileBlob("dir4/dir5/file5.png", FileBlobId.from("id-4"))

        val tree2 = buildTree(
            id = TreeId.from("tree-2"),
            entries = listOf(
                FileTreeEntry(path = fileBlob1.path, fileBlobId = fileBlob1.id),
            )
        )
        val tree3 = buildTree(
            id = TreeId.from("tree-3"),
            entries = listOf(
                FileTreeEntry(path = fileBlob4.path, fileBlobId = fileBlob4.id),
            )
        )
        val tree5 = buildTree(
            id = TreeId.from("tree-5"),
            entries = listOf(
                FileTreeEntry(path = fileBlob5.path, fileBlobId = fileBlob5.id),
            )
        )
        val tree4 = buildTree(
            id = TreeId.from("tree-4"),
            entries = listOf(
                SubtreeTreeEntry(path = "dir4/dir5", subtreeId = tree5.id),
            )
        )
        val tree1 = buildTree(
            id = TreeId.from("tree-1"),
            entries = listOf(
                FileTreeEntry(path = fileBlob2.path, fileBlobId = fileBlob2.id),
                FileTreeEntry(path = fileBlob3.path, fileBlobId = fileBlob3.id),
                SubtreeTreeEntry(path = "dir2", subtreeId = tree2.id),
                SubtreeTreeEntry(path = "dir3", subtreeId = tree3.id),
                SubtreeTreeEntry(path = "dir4", subtreeId = tree4.id),
            )
        )

        val expectedResult = mapOf(
            "" to listOf(fileBlob2, fileBlob3),
            "dir2" to listOf(fileBlob1),
            "dir3" to listOf(fileBlob4),
            "dir4" to emptyList(),
            "dir4/dir5" to listOf(fileBlob5),
        )

        val treeProvider = mockk<(treeId: TreeId) -> Tree>()

        every { treeProvider(tree2.id) } returns tree2
        every { treeProvider(tree3.id) } returns tree3
        every { treeProvider(tree1.id) } returns tree1
        every { treeProvider(tree4.id) } returns tree4
        every { treeProvider(tree5.id) } returns tree5

        val result = groupFilesByFolderFromTree(
            root = root,
            tree = tree1,
            treeProvider = treeProvider
        )

        assertEquals(expectedResult, result)
    }

    @Test
    fun `mergeGroupedFiles - groups them correctly`() {
        val baseFileBlob = mapOf(
            "./dir2" to listOf(FileBlob("./dir2/file1.jpg", FileBlobId.from("base-id-1"))),
            "." to listOf(FileBlob("./file2.pdf", FileBlobId.from("base-id-2"))),
        )
        val overrideFileBlob = mapOf(
            "." to listOf(
                FileBlob("./file2.pdf", FileBlobId.from("override-id-2")),
                FileBlob("./file6.doc", FileBlobId.from("override-id-3"))
            ),
            "./dir4" to listOf(FileBlob("./dir4/file5.png", FileBlobId.from("override-id-4"))),
        )

        val expectedResult = mapOf(
            "./dir2" to listOf(FileBlob("./dir2/file1.jpg", FileBlobId.from("base-id-1"))),
            "." to listOf(
                FileBlob("./file2.pdf", FileBlobId.from("override-id-2")),
                FileBlob("./file6.doc", FileBlobId.from("override-id-3"))
            ),
            "./dir4" to listOf(FileBlob("./dir4/file5.png", FileBlobId.from("override-id-4"))),
        )

        val result = mergeGroupedFiles(base = baseFileBlob, override = overrideFileBlob)

        assertEquals(expectedResult, result)
    }
}
