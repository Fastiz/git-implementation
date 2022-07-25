package dao.objects.head

import dao.objects.files.FileDao
import dao.objects.files.FileDaoImpl

typealias Hash = String

class NoHeadException : RuntimeException()

class HeadDaoImpl(private val fileDao: FileDao = FileDaoImpl()) : HeadDao {
    override fun getHead(): Hash {
        val line = fileDao.readFile(HEAD_PATH) {
            readLine() ?: throw NoHeadException()
        }

        return line.substringAfter("ref: ")
    }

    override fun setHead(commitId: String) {
        fileDao.writeFile(HEAD_PATH) {
            write(commitId)
        }
    }

    companion object {
        const val HEAD_PATH = ".git-fastiz/HEAD"
    }
}