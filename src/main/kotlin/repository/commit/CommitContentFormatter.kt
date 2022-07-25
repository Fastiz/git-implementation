package repository.commit

import model.Commit

object CommitContentFormatter {
    fun formatCommitMessage(
        tree: String,
        parent: String,
        message: String
    ): String {
        val sb = StringBuilder()

        sb.appendLine("tree $tree")
        sb.appendLine("parent $parent")
        sb.appendLine()
        sb.appendLine(message)

        return sb.toString()
    }

    fun parseCommitMessage(
        commitId: String,
        commit: String
    ): Commit {
        val lines = commit.split("\n")

        val treeId = lines[0].substringAfter("tree ")
        val parentId = lines[1].substringAfter("parent ")

        return Commit(
            id = commitId,
            treeId = treeId,
            parentId = parentId,
            message = ""
        )
    }
}