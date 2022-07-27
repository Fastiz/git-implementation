package dao.objects.head

import dao.objects.files.FileDao
import dao.objects.files.FileDaoImpl
import model.File

class NoHeadException : RuntimeException()

class HeadDaoImpl(private val fileDao: FileDao = FileDaoImpl()) : HeadDao {
    override fun getHead(): String {
        val line = fileDao.readFile(File.HEAD.path) {
            readLine() ?: throw NoHeadException()
        }

        return line
    }

    override fun setHead(commitId: String) {
        fileDao.writeFile(File.HEAD.path) {
            write(commitId)
        }
    }
}