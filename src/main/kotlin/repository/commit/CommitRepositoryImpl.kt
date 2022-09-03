package repository.commit

import dao.objects.ObjectsDao
import model.Commit
import repository.commit.CommitContentFormatter.formatCommitMessage
import repository.commit.CommitContentFormatter.parseCommitMessage

class CommitRepositoryImpl(private val objectsDao: ObjectsDao) : CommitRepository {
    override fun create(treeId: String, parentId: String?, message: String): Hash {
        val content = formatCommitMessage(treeId, parentId, message)

        return objectsDao.createFromString(content)
    }

    override fun get(commitId: Hash): Commit {
        val commitString = objectsDao.get(commitId)

        return parseCommitMessage(
            commitId = commitId,
            commit = commitString
        )
    }
}