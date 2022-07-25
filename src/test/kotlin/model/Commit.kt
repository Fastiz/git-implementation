package model

fun buildCommit(
    id: String = "commit-id",
    message: String = "message",
    parentId: String = "parent-id",
    treeId: String = "tree-id",
): Commit {
    return Commit(
        id = id,
        message = message,
        parentId = parentId,
        treeId = treeId
    )
}
