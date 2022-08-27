package dao.files

class WriterImpl() : Writer {
    private val fullContent = StringBuilder("")

    override fun write(content: String) {
        fullContent.append(content)
    }

    fun getFullContent(): String{
        return fullContent.toString()
    }
}