package repository.tree

import dao.objects.ObjectsDao
import model.Tree
import model.TreeInput
import repository.tree.TreeContentFormatter.formatTreeContent
import repository.tree.TreeContentFormatter.mapTreeFromBlob

class TreeRepositoryImpl(private val objectsDao: ObjectsDao) : TreeRepository {
    override fun create(treeInput: TreeInput): Hash {
        val content = formatTreeContent(treeInput)

        return objectsDao.createFromString(content)
    }

    override fun get(treeId: Hash): Tree {
        val blob = objectsDao.get(treeId)

        return mapTreeFromBlob(treeId, blob)
    }
}