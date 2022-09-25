package repository.head

import dao.files.FileDao
import directory.Head
import model.CommitId

class HeadRepositoryImpl(
    private val head: Head,
    private val fileDao: FileDao
) : HeadRepository {
    override fun getHead(): CommitId? {
        val content = fileDao.readFile(head.path) {
            readLine()
        }

        val refString = content?.substringAfter("ref: ") ?: return null

        return CommitId.from(refString)
    }

    override fun setHead(commitId: CommitId) {
        fileDao.writeFile(head.path) {
            write(commitId.value)
        }
    }
}
