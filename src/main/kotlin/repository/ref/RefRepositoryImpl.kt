package repository.ref

import dao.files.FileDao
import directory.RefsHeads
import model.CommitId

class RefRepositoryImpl(
    private val refsHeads: RefsHeads,
    private val fileDao: FileDao
) : RefRepository {
    override fun get(refName: String): CommitId? {
        val fullPath = refsHeads.extend(refName)
        if (fileDao.doesFileExist(fullPath)) {
            val readCommit = fileDao.readFile(fullPath) {
                readLine()
            } ?: throw IllegalStateException("Ref exists but is empty")

            return CommitId.from(readCommit)
        }
        return null
    }

    override fun set(refName: String, commitId: CommitId) {
        val fullPath = refsHeads.extend(refName)

        if (!fileDao.doesFileExist(fullPath)) {
            fileDao.createFile(fullPath)
        }

        fileDao.writeFile(fullPath) {
            writeLine(commitId.value)
        }
    }
}