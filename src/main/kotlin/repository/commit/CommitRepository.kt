package repository.commit

import model.Commit
import model.CommitId
import model.TreeId

interface CommitRepository {
    fun create(
        treeId: TreeId,
        parentCommitId: CommitId?,
        message: String
    ): CommitId

    fun get(commitId: CommitId): Commit
}