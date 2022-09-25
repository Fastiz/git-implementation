package dao.files

class WriterImpl() : Writer {
    private val fullContent = StringBuilder("")

    override fun write(content: String) {
        fullContent.append(content)
    }

    override fun writeLine(line: String) {
        fullContent.appendLine(line)
    }

    override fun getFullContent(): String {
        return fullContent.toString()
    }
}
