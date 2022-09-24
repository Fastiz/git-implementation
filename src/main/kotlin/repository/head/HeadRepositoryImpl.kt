package repository.head

import dao.files.FileDao
import model.CommitId
import model.File

class HeadRepositoryImpl(private val fileDao: FileDao) : HeadRepository {
    override fun getHead(): CommitId? {
        val content = fileDao.readFile(File.HEAD.path) {
            readLine()
        }

        val refString = content?.substringAfter("ref: ") ?: return null

        return CommitId.from(refString)
    }

    override fun setHead(commitId: CommitId) {
        fileDao.writeFile(File.HEAD.path) {
            write(commitId.value)
        }
    }
}