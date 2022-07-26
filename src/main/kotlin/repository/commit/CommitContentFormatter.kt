package repository.commit

import model.Commit

object CommitContentFormatter {
    fun formatCommitMessage(
        treeId: String,
        parentId: String,
        message: String
    ): String {
        val sb = StringBuilder()

        sb.appendLine("tree $treeId")
        sb.appendLine("parent $parentId")
        sb.appendLine()
        sb.append(message)

        return sb.toString()
    }

    fun parseCommitMessage(
        commitId: String,
        commit: String
    ): Commit {
        val lines = commit.split("\n")

        val treeId = lines[0].substringAfter("tree ")
        val parentId = lines[1].substringAfter("parent ")
        val message = lines[3]

        return Commit(
            id = commitId,
            treeId = treeId,
            parentId = parentId,
            message = message
        )
    }
}