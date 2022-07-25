package service.commit

import model.FileBlob
import model.overrideWith
import repository.blob.FileBlobRepository
import repository.blob.FileBlobRepositoryImpl
import repository.commit.CommitRepository
import repository.commit.CommitRepositoryImpl
import repository.head.HeadRepository
import repository.head.HeadRepositoryImpl
import repository.tree.TreeRepository
import repository.tree.TreeRepositoryImpl

typealias Path = String

class CommitServiceImpl(
    private val treeRepository: TreeRepository = TreeRepositoryImpl(),
    private val commitRepository: CommitRepository = CommitRepositoryImpl(),
    private val refRepository: HeadRepository = HeadRepositoryImpl(),
    private val fileBlobRepository: FileBlobRepository = FileBlobRepositoryImpl()
) : CommitService {

    override fun run(stagedFiles: List<Path>) {
        val head = refRepository.getHead()

        val currentCommit = commitRepository.get(head)

        val fileBlobList = stagedFiles.map {
            val id = fileBlobRepository.create(it)

            FileBlob(id = id, path = it)
        }

        val currentTree = treeRepository.get(currentCommit.treeId)

        val mergedBlobFiles = currentTree.fileBlobList.overrideWith(fileBlobList)

        val treeId = treeRepository.create(mergedBlobFiles)

        val commitId = commitRepository.create(
            treeId = treeId,
            parentId = currentCommit.id,
            message = ""
        )

        refRepository.setHead(commitId)
    }
}