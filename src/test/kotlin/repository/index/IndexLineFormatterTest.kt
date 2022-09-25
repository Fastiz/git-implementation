package repository.index

import model.FileBlob
import model.FileBlobId
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class IndexLineFormatterTest {

    @Test
    fun parseLineToBlob() {
        val line = "file-id file-path"

        val result = IndexLineFormatter.parseLineToBlob(line)

        val expectedResult = FileBlob(id = FileBlobId.from("file-id"), path = "file-path")

        assertEquals(expectedResult, result)
    }
}
