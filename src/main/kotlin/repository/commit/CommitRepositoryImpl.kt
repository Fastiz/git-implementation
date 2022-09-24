package repository.commit

import dao.objects.ObjectsDao
import model.Commit
import model.CommitId
import model.TreeId
import repository.commit.CommitContentFormatter.formatCommitMessage
import repository.commit.CommitContentFormatter.parseCommitMessage

class CommitRepositoryImpl(private val objectsDao: ObjectsDao) : CommitRepository {
    override fun create(treeId: TreeId, parentCommitId: CommitId?, message: String): CommitId {
        val content = formatCommitMessage(treeId, parentCommitId, message)

        val createdObjectId = objectsDao.createFromString(content)

        return CommitId.from(createdObjectId)
    }

    override fun get(commitId: CommitId): Commit {
        val commitString = objectsDao.get(commitId.value)

        return parseCommitMessage(
            commitId = commitId,
            commit = commitString
        )
    }
}
