package service.commit.step

import org.junit.Test
import service.commit.step.DirectoriesParser.getAllDirectories
import service.commit.step.DirectoriesParser.getDirectChildrenDirectories
import service.commit.step.DirectoriesParser.isChildrenDirectory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class DirectoriesParserTest {

    @Test
    fun `getChildrenDirectories - get only the children directories`() {
        val directory = "dir1"
        val allDirectories = listOf(
            "dir1/dir2",
            "dir1/dir2/dir3",
            "dir1/dir4",
            "dir1/dir4/dir5",
            "dir1/dir4/dir6",
        )

        val expectedResult = listOf(
            "dir1/dir2",
            "dir1/dir4"
        )

        val result = getDirectChildrenDirectories(directory, allDirectories)

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

    @Test
    fun `isChildrenDirectory - should return true when is a children directory`() {
        val directory = "some/directory/path"
        val other = "$directory/dir"

        val result = isChildrenDirectory(directory, other)

        assertTrue(result)
    }

    @Test
    fun `isChildrenDirectory - should return false when is not children`() {
        val directory = "some/directory/path"
        val other = "$directory/dir1/dir2"

        val result = isChildrenDirectory(directory, other)

        assertFalse(result)
    }

    @Test
    fun `isChildrenDirectory - if directory is empty - should return true when is a children directory`() {
        val directory = ""
        val other = "dir"

        val result = isChildrenDirectory(directory, other)

        assertTrue(result)
    }

    @Test
    fun `isChildrenDirectory - if directory is empty - should return false when is not children`() {
        val directory = ""
        val other = "dir1/dir2"

        val result = isChildrenDirectory(directory, other)

        assertFalse(result)
    }
}
