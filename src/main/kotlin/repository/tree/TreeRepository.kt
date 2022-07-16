package repository.tree

import model.FileBlob
import model.Tree

typealias Hash = String

interface TreeRepository {
    fun create(fileBlobList: List<FileBlob>): Hash

    fun get(treeId: Hash): Tree
}