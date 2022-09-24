package repository.commit

import model.Commit
import model.CommitId
import model.TreeId

object CommitContentFormatter {
    fun formatCommitMessage(
        treeId: TreeId,
        parentId: CommitId?,
        message: String
    ): String {
        val sb = StringBuilder()

        val parentIdText = parentId?.value ?: ""

        sb.appendLine("tree ${treeId.value}")
        sb.appendLine("parent $parentIdText")
        sb.appendLine()
        sb.appendLine(message)

        return sb.toString()
    }

    fun parseCommitMessage(
        commitId: CommitId,
        commit: String
    ): Commit {
        val lines = commit.split("\n")

        val treeId = lines[0].substringAfter("tree ")
        val readParentId = lines[1].substringAfter("parent ")
        val message = lines[3]

        val parentId = if (readParentId == "") {
            null
        } else {
            CommitId.from(readParentId)
        }

        return Commit(
            id = commitId,
            treeId = TreeId.from(treeId),
            parentId = parentId,
            message = message
        )
    }
}
