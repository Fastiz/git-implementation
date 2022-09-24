package repository.commit

import model.Commit
import model.CommitId

interface CommitRepository {
    fun create(
        treeId: String,
        parentCommitId: CommitId?,
        message: String
    ): CommitId

    fun get(commitId: CommitId): Commit
}