package service.commit.step

import model.Step
import model.overrideWith
import repository.tree.TreeRepository
import repository.tree.TreeRepositoryImpl

data class OutputCreateNewTree(
    val treeId: String,
    val parentId: String,
)

class CreateNewTree(
    private val treeRepository: TreeRepository = TreeRepositoryImpl(),
) : Step<CreateFileBlobsIfNotExistOutput, OutputCreateNewTree> {
    override fun execute(input: CreateFileBlobsIfNotExistOutput): OutputCreateNewTree {
        val mergedBlobFiles = input.currentTree.fileBlobList.overrideWith(input.fileBlobList)

        val treeId = treeRepository.create(mergedBlobFiles)

        return OutputCreateNewTree(
            treeId = treeId,
            parentId = input.commitId
        )
    }
}