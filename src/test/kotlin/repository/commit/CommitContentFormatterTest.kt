package repository.commit

import model.CommitId
import model.TreeId
import org.junit.Test
import repository.commit.CommitContentFormatter.formatCommitMessage
import repository.commit.CommitContentFormatter.parseCommitMessage
import kotlin.test.assertEquals

class CommitContentFormatterTest {

    @Test
    fun `formatCommitMessage - formats the commit correctly`() {
        val result = formatCommitMessage(
            TreeId.from("tree-id"),
            CommitId.from("parent-id"),
            "message",
        )

        val expected = "" +
            "tree tree-id\n" +
            "parent parent-id\n" +
            "\n" +
            "message\n"

        assertEquals(expected, result)
    }

    @Test
    fun `parseCommitMessage - parses the commit correctly`() {
        val commitContent = "" +
            "tree tree-id\n" +
            "parent parent-id\n" +
            "\n" +
            "message"

        val result = parseCommitMessage(CommitId.from("commit-id"), commitContent)

        assertEquals(TreeId.from("tree-id"), result.treeId)
        assertEquals(CommitId.from("parent-id"), result.parentId)
        assertEquals(CommitId.from("commit-id"), result.id)
        assertEquals("message", result.message)
    }
}
