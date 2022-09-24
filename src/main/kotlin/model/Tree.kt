package model

data class TreeId(val value: String) {
    companion object {
        fun from(value: String) = TreeId(value)
    }
}

data class Tree(val id: TreeId, val entries: List<TreeEntry>)

interface TreeEntry

data class FileTreeEntry(val path: String, val fileBlobId: FileBlobId) : TreeEntry

data class SubtreeTreeEntry(val path: String, val subtreeId: TreeId) : TreeEntry