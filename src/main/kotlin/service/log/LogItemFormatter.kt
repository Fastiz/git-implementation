package service.log

object LogItemFormatter {
    fun formatLogItem(commitId: String): String {
        val sb = StringBuilder("")

        sb.appendLine("Commit: $commitId")

        return sb.toString()
    }
}