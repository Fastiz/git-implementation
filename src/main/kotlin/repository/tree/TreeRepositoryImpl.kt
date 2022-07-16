package repository.tree

import dao.objects.ObjectsDao
import dao.objects.ObjectsDaoImpl
import model.FileBlob
import model.Tree
import repository.tree.TreeContentFormatter.formatTreeContent
import repository.tree.TreeContentFormatter.mapTreeFromBlob

class TreeRepositoryImpl(private val objectsDao: ObjectsDao = ObjectsDaoImpl()) : TreeRepository {
    override fun create(fileBlobList: List<FileBlob>): Hash {
        val content = formatTreeContent(fileBlobList)

        return objectsDao.createFromString(content)
    }

    override fun get(treeId: Hash): Tree {
        val blob = objectsDao.get(treeId)

        return mapTreeFromBlob(treeId, blob)
    }
}