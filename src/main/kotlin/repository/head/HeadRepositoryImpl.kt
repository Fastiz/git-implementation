package repository.head

import dao.files.FileDao
import model.File

class HeadRepositoryImpl(private val fileDao: FileDao) : HeadRepository {
    override fun getHead(): Hash? {
        val content = fileDao.readFile(File.HEAD.path) {
            readLine()
        }

        return content?.substringAfter("ref: ")
    }

    override fun setHead(commitId: String) {
        fileDao.writeFile(File.HEAD.path) {
            write(commitId)
        }
    }
}