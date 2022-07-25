package service.commit.step

import model.FileBlob
import model.Step
import model.Tree
import repository.blob.FileBlobRepository
import repository.blob.FileBlobRepositoryImpl
import repository.commit.CommitRepository
import repository.commit.CommitRepositoryImpl
import repository.head.HeadRepository
import repository.head.HeadRepositoryImpl
import repository.tree.TreeRepository
import repository.tree.TreeRepositoryImpl

data class CreateFileBlobsIfNotExistInput(
    val stagedFiles: List<String>
)

data class CreateFileBlobsIfNotExistOutput(
    val fileBlobList: List<FileBlob>,
    val currentTree: Tree,
    val commitId: String,
)

class CreateFileBlobsIfNotExist(
    private val treeRepository: TreeRepository = TreeRepositoryImpl(),
    private val commitRepository: CommitRepository = CommitRepositoryImpl(),
    private val headRepository: HeadRepository = HeadRepositoryImpl(),
    private val fileBlobRepository: FileBlobRepository = FileBlobRepositoryImpl()
) : Step<CreateFileBlobsIfNotExistInput, CreateFileBlobsIfNotExistOutput> {

    override fun execute(input: CreateFileBlobsIfNotExistInput): CreateFileBlobsIfNotExistOutput {
        val head = headRepository.getHead()
        val currentCommit = commitRepository.get(head)
        val currentTree = treeRepository.get(currentCommit.treeId)

        val fileBlobList = input.stagedFiles.map {
            val id = fileBlobRepository.createIfNotExists(it)

            FileBlob(id = id, path = it)
        }

        return CreateFileBlobsIfNotExistOutput(
            fileBlobList = fileBlobList,
            currentTree = currentTree,
            commitId = currentCommit.id
        )
    }
}