package model

data class CommitId(val value: String) {
    companion object {
        fun from(value: String) = CommitId(value)
    }
}

data class Commit(
    val id: CommitId,
    val message: String,
    val parentId: CommitId?,
    val treeId: TreeId,
)
