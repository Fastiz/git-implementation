package repository.commit

import dao.objects.objects.ObjectsDao
import dao.objects.objects.ObjectsDaoImpl
import model.Commit
import repository.commit.CommitContentFormatter.formatCommitMessage

class CommitRepositoryImpl(private val objectsDao: ObjectsDao = ObjectsDaoImpl()) : CommitRepository {
    override fun create(treeId: String, parentId: String, message: String): Hash {
        val content = formatCommitMessage(treeId, parentId, message)

        return objectsDao.createFromString(content)
    }

    override fun get(commit: Hash): Commit {
        // TODO: read
        objectsDao.get(commit)

        return Commit(
            id = commit,
            message = "",
            parentId = "",
            treeId = ""
        )
    }
}