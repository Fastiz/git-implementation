package repository.tree

import model.Tree
import model.TreeInput

typealias Hash = String

interface TreeRepository {
    fun create(treeInput: TreeInput): Hash

    fun get(treeId: Hash): Tree
}