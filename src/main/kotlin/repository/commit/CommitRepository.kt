package repository.commit

import model.Commit

typealias Hash = String

interface CommitRepository {
    fun create(
        treeId: String,
        parentId: String?,
        message: String
    ): Hash

    fun get(commitId: Hash): Commit
}