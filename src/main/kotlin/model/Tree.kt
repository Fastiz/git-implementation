package model

data class Tree(val id: String, val entries: List<TreeEntry>)

interface TreeEntry

data class FileTreeEntry(val path: String, val fileBlobId: String) : TreeEntry

data class SubtreeTreeEntry(val path: String, val subtreeId: String) : TreeEntry