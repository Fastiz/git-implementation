package model

object TreeDataProvider {
    fun buildTree(
        id: String = "tree-id",
        entries: List<TreeEntry> = emptyList()
    ): Tree {
        return Tree(
            id = id,
            entries = entries
        )
    }
}
