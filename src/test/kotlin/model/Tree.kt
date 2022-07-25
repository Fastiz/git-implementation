package model

fun buildTree(
    id: String = "tree-id",
    fileBlobList: List<FileBlob> = emptyList()
): Tree {
    return Tree(
        id = id,
        fileBlobList = fileBlobList
    )
}