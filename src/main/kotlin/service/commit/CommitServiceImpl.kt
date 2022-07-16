package service.commit

import model.FileBlob
import model.overrideWith
import repository.blobRepository.BlobRepository
import repository.blobRepository.BlobRepositoryImpl
import repository.commit.CommitRepository
import repository.commit.CommitRepositoryImpl
import repository.refs.RefRepository
import repository.refs.RefRepositoryImpl
import repository.tree.TreeRepository
import repository.tree.TreeRepositoryImpl

typealias Path = String

class CommitImpl(
    private val treeRepository: TreeRepository = TreeRepositoryImpl(),
    private val commitRepository: CommitRepository = CommitRepositoryImpl(),
    private val refRepository: RefRepository = RefRepositoryImpl(),
    private val blobRepository: BlobRepository = BlobRepositoryImpl()
) : CommitService {

    override fun run(stagedFiles: List<Path>) {
        val head = refRepository.getHead()

        val fileBlobList = stagedFiles.map {
            val id = blobRepository.createFromFile(it)

            FileBlob(id = id, path = it)
        }

        val currentTree = treeRepository.get(head.treeId)

        val mergedBlobFiles = currentTree.fileBlobList.overrideWith(fileBlobList)

        val treeId = treeRepository.create(mergedBlobFiles)

        val commitId = commitRepository.create(
            treeId = treeId,
            parentId = head.id,
            message = ""
        )

        refRepository.setHead(commitId)
    }
}