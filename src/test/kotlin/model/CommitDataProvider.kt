package model

object CommitDataProvider {
    fun buildCommit(
        id: CommitId = CommitId.from("commit-id"),
        message: String = "message",
        parentId: CommitId = CommitId.from("parent-id"),
        treeId: String = "tree-id",
    ): Commit {
        return Commit(
            id = id,
            message = message,
            parentId = parentId,
            treeId = treeId
        )
    }
}

