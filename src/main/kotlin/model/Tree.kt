package model

data class Tree(val id: String, val entries: List<TreeEntry>)

interface TreeEntry

class FileTreeEntry(val path: String, val fileBlobId: String) : TreeEntry

class SubtreeTreeEntry(val path: String, val subtreeId: String) : TreeEntry