package repository.tree

import model.FileBlob
import model.Tree
import repository.blobRepository.BlobRepository
import repository.blobRepository.BlobRepositoryImpl
import repository.tree.TreeContentFormatter.formatTreeContent
import repository.tree.TreeContentFormatter.mapTreeFromBlob

class TreeRepositoryImpl(private val blobRepository: BlobRepository = BlobRepositoryImpl()) : TreeRepository {
    override fun create(fileBlobList: List<FileBlob>): Hash {
        val content = formatTreeContent(fileBlobList)

        return blobRepository.createFromString(content)
    }

    override fun get(treeId: Hash): Tree {
        val blob = blobRepository.get(treeId)

        return mapTreeFromBlob(treeId, blob)
    }
}