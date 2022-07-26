package repository.commit

import org.junit.Test
import repository.commit.CommitContentFormatter.formatCommitMessage
import repository.commit.CommitContentFormatter.parseCommitMessage
import kotlin.test.assertEquals

class CommitContentFormatterTest {

    @Test
    fun `formatCommitMessage - formats the commit correctly`() {
        val result = formatCommitMessage(
            "tree-id",
            "parent-id",
            "message",
        )

        val expected = "" +
                "tree tree-id\n" +
                "parent parent-id\n" +
                "\n" +
                "message"

        assertEquals(expected, result)
    }

    @Test
    fun `parseCommitMessage - parses the commit correctly`() {
        val commitContent = "" +
                "tree tree-id\n" +
                "parent parent-id\n" +
                "\n" +
                "message"

        val result = parseCommitMessage("commit-id", commitContent)

        assertEquals("tree-id", result.treeId)
        assertEquals("parent-id", result.parentId)
        assertEquals("commit-id", result.id)
        assertEquals("message", result.message)
    }
}