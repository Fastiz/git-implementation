package model

data class Commit(
    val id: String,
    val message: String,
    val parentId: String,
    val treeId: String,
)
