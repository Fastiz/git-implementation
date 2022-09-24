package service.log

import model.Commit
import model.CommitId
import repository.commit.CommitRepository

class CommitHistoryFactory(
    private val commitRepository: CommitRepository
) {
    fun create(startCommitId: CommitId): CommitHistoryIterable {
        return CommitHistoryIterable(
            startCommitId
        ) { commitId -> commitRepository.get(commitId) }
    }
}

class CommitHistoryIterable(
    private val startCommitId: CommitId,
    private val commitSupplier: (commitId: CommitId) -> Commit
) : Iterable<Commit> {
    override fun iterator(): Iterator<Commit> {
        return CommitHistoryIterator(
            startCommitId,
            commitSupplier
        )
    }

    private class CommitHistoryIterator(
        startCommitId: CommitId,
        private val commitSupplier: (commitId: CommitId) -> Commit
    ) : Iterator<Commit> {
        private var nextCommit: CommitId?

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
