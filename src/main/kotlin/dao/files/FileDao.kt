package dao.files

interface Writer {
    fun write(content: String)
}

interface Reader {
    fun readLine(): String?
}

interface FileDao {
    fun createDirectory(path: String)

    fun createFile(path: String)

    fun writeFile(path: String, executor: Writer.() -> Unit)

    fun <T> readFile(path: String, executor: Reader.() -> T): T

    fun copyFile(origin: String, target: String)

    fun removeAllExcluding(directory: String, excluding: List<String>)

    fun doesFileExist(path: String): Boolean
}
