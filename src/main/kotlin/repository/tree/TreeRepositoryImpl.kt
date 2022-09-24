package repository.tree

import dao.objects.ObjectsDao
import model.Tree
import model.TreeId
import model.TreeInput
import repository.tree.TreeContentFormatter.formatTreeContent
import repository.tree.TreeContentFormatter.mapTreeFromBlob

class TreeRepositoryImpl(private val objectsDao: ObjectsDao) : TreeRepository {
    override fun create(treeInput: TreeInput): TreeId {
        val content = formatTreeContent(treeInput)

        val objectId = objectsDao.createFromString(content)

        return TreeId.from(objectId)
    }

    override fun get(treeId: TreeId): Tree {
        val blob = objectsDao.get(treeId.value)

        return mapTreeFromBlob(treeId, blob)
    }
}