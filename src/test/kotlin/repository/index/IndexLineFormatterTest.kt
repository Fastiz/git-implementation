package repository.index

import model.FileBlob
import model.FileBlobId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class IndexLineFormatterTest {

    @Test
    fun parseLineToBlob() {
        val line = "file-id file-path"

        val result = IndexLineFormatter.parseLineToBlob(line)

        val expectedResult = FileBlob(id = FileBlobId.from("file-id"), path = "file-path")

        assertEquals(expectedResult, result)
    }
}
