package repository.tree

import model.FileBlobId
import model.FileTreeEntry
import model.Tree
import model.TreeId
import model.TreeInput
import org.junit.Test
import repository.tree.TreeContentFormatter.formatTreeContent
import repository.tree.TreeContentFormatter.mapTreeFromBlob
import kotlin.test.assertEquals

class TreeContentFormatterTest {
    @Test
    fun `formatTreeContent - creates correct string`() {
        val entries = listOf(
            FileTreeEntry(path = "file-1.kt", fileBlobId = FileBlobId.from("id-1")),
            FileTreeEntry(path = "file-2.kt", fileBlobId = FileBlobId.from("id-2")),
        )
        val treeInput = TreeInput(entries = entries)

        val result = formatTreeContent(treeInput)

        val expected = "" +
                "blob id-1 file-1.kt\n" +
                "blob id-2 file-2.kt\n"

        assertEquals(expected, result)
    }

    @Test
    fun `mapTreeFromBlob - create correct tree`() {
        val treeContent = "" +
                "blob id-1 file-1.kt\n" +
                "blob id-2 file-2.kt\n"

        val result = mapTreeFromBlob(TreeId.from("tree-id"), treeContent)

        val expectedTree = Tree(
            id = TreeId.from("tree-id"),
            entries = listOf(
                FileTreeEntry(path = "file-1.kt", fileBlobId = FileBlobId.from("id-1")),
                FileTreeEntry(path = "file-2.kt", fileBlobId = FileBlobId.from("id-2")),
            )
        )

        assertEquals(TreeId.from("tree-id"), result.id)
        assertEquals(
            expectedTree,
            result
        )
    }
}
