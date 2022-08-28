package dao.head

import dao.files.FileDao
import dao.files.FileDaoImpl
import model.File

class HeadDaoImpl(private val fileDao: FileDao = FileDaoImpl()) : HeadDao {
    override fun getHead(): String? {
        val line = fileDao.readFile(File.HEAD.path) {
            readLine()
        }

        return line
    }

    override fun setHead(commitId: String) {
        fileDao.writeFile(File.HEAD.path) {
            write(commitId)
        }
    }
}