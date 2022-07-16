package repository.commit

import dao.objects.ObjectsDao
import dao.objects.ObjectsDaoImpl
import repository.commit.CommitContentFormatter.formatCommitMessage

class CommitRepositoryImpl(private val objectsDao: ObjectsDao = ObjectsDaoImpl()) : CommitRepository {
    override fun create(treeId: String, parentId: String, message: String): Hash {
        val content = formatCommitMessage(treeId, parentId, message)

        return objectsDao.createFromString(content)
    }
}