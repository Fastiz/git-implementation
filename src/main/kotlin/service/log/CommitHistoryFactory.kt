package service.log

import model.Commit
import repository.commit.CommitRepository
import repository.commit.CommitRepositoryImpl

class CommitHistoryFactory(
    private val commitRepository: CommitRepository = CommitRepositoryImpl()
) {
    fun create(startCommitId: String): CommitHistoryIterable {
        return CommitHistoryIterable(
            startCommitId
        ) { commitId -> commitRepository.get(commitId) }
    }
}

class CommitHistoryIterable(
    private val startCommitId: String,
    private val commitSupplier: (commitId: String) -> Commit
) : Iterable<Commit> {
    override fun iterator(): Iterator<Commit> {
        return CommitHistoryIterator(
            startCommitId,
            commitSupplier
        )
    }

    private class CommitHistoryIterator(
        startCommitId: String,
        private val commitSupplier: (commitId: String) -> Commit
    ) : Iterator<Commit> {
        private var nextCommit: String?

        init {
            nextCommit = startCommitId
        }

        override fun hasNext(): Boolean {
            return nextCommit != null
        }

        override fun next(): Commit {
            val commitId = nextCommit ?: throw Exception("Method next called even though there is no next element")

            val commit = commitSupplier(commitId)

            nextCommit = commit.parentId

            return commit
        }
    }
}
