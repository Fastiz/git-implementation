package service.commit.step

import model.FileBlob
import org.junit.jupiter.api.Test

import service.commit.step.GroupFiles.groupFilesByFolder
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
}