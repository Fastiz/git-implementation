package model

object TreeDataProvider {
    fun buildTree(
        id: TreeId = TreeId.from("tree-id"),
        entries: List<TreeEntry> = emptyList()
    ): Tree {
        return Tree(
            id = id,
            entries = entries
        )
    }
}
