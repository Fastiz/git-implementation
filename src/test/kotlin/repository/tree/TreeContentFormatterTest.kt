package repository.tree

import model.FileBlob
import org.junit.Test
import repository.tree.TreeContentFormatter.formatTreeContent
import repository.tree.TreeContentFormatter.mapTreeFromBlob
import kotlin.test.assertEquals

class TreeContentFormatterTest {
    @Test
    fun `formatTreeContent - creates correct string`() {
        val stagedFiles = listOf(
            FileBlob(path = "file-1.kt", id = "id-1"),
            FileBlob(path = "file-2.kt", id = "id-2"),
        )

        val result = formatTreeContent(stagedFiles)

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

        val result = mapTreeFromBlob("tree-id", treeContent)

        assertEquals("tree-id", result.id)
        assertEquals(
            listOf(
                FileBlob(id = "id-1", path = "file-1.kt"),
                FileBlob(id = "id-2", path = "file-2.kt"),
            ),
            result.fileBlobList
        )
    }
}