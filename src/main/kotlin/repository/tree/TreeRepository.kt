package repository.tree

import model.Tree
import model.TreeId
import model.TreeInput

interface TreeRepository {
    fun create(treeInput: TreeInput): TreeId

    fun get(treeId: TreeId): Tree
}
