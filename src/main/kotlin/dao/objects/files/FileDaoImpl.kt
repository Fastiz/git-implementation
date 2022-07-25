package dao.objects.files

class FileDaoImpl : FileDao {
    override fun createDirectory(path: String) {
        TODO("Not yet implemented")
    }

    override fun createFile(path: String) {
        TODO("Not yet implemented")
    }

    override fun writeFile(path: String, executor: Writer.() -> Unit) {
        TODO("Not yet implemented")
    }

    override fun <T> readFile(path: String, executor: Reader.() -> T): T {
        TODO("Not yet implemented")
    }

    override fun copyFile(origin: String, target: String) {
        TODO("Not yet implemented")
    }
}