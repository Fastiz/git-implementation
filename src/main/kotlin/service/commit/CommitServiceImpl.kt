package service.commit

import model.FileBlob
import model.overrideWith
import repository.blobRepository.FileBlobRepository
import repository.blobRepository.FileBlobRepositoryImpl
import repository.commit.CommitRepository
import repository.commit.CommitRepositoryImpl
import repository.refs.RefRepository
import repository.refs.RefRepositoryImpl
import repository.tree.TreeRepository
import repository.tree.TreeRepositoryImpl

typealias Path = String

class CommitServiceImpl(
    private val treeRepository: TreeRepository = TreeRepositoryImpl(),
    private val commitRepository: CommitRepository = CommitRepositoryImpl(),
    private val refRepository: RefRepository = RefRepositoryImpl(),
    private val fileBlobRepository: FileBlobRepository = FileBlobRepositoryImpl()
) : CommitService {

    override fun run(stagedFiles: List<Path>) {
        val head = refRepository.getHead()

        val fileBlobList = stagedFiles.map {
            val id = fileBlobRepository.create(it)

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