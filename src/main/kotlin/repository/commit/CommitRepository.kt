package repository.commit

typealias Hash = String

interface CommitRepository {
    fun create(
        treeId: String,
        parentId: String,
        message: String
    ): Hash
}