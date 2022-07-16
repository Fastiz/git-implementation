package repository.commit

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
}