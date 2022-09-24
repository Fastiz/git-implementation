package service.commit.step

import org.junit.Test
import service.commit.step.DirectoriesParser.getAllDirectories
import service.commit.step.DirectoriesParser.getChildrenDirectories
import kotlin.test.assertEquals

internal class DirectoriesParserTest {

    @Test
    fun `getChildrenDirectories - get only the children directories`() {
        val directory = "/dir1"
        val allDirectories = listOf(
            "/dir1/dir2",
            "/dir1/dir2/dir3",
            "/dir1/dir4",
            "/dir1/dir4/dir5",
            "/dir1/dir4/dir6",
        )

        val expectedResult = listOf(
            "/dir1/dir2",
            "/dir1/dir4"
        )

        val result = getChildrenDirectories(directory, allDirectories)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `getAllDirectories - gets all the directories`() {
        val directoriesWithContent = listOf(
            "./dir1/dir2/dir3",
            "./dir1/dir4/dir5",
            "./dir1/dir4/dir6",
        )
        val expectedResult = listOf(
            ".",
            "./dir1",
            "./dir1/dir2",
            "./dir1/dir4",
            "./dir1/dir2/dir3",
            "./dir1/dir4/dir5",
            "./dir1/dir4/dir6",
        )

        val result = getAllDirectories(directoriesWithContent)

        assertEquals(expectedResult.toSet(), result.toSet())
    }
}
