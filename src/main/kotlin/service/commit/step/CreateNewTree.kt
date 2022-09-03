package service.commit.step

import model.FileBlob
import model.Step
import model.overrideWith
import repository.commit.CommitRepository
import repository.head.HeadRepository
import repository.tree.TreeRepository

data class OutputCreateNewTree(
    val treeId: String,
)

class CreateNewTree(
    private val commitRepository: CommitRepository,
    private val headRepository: HeadRepository,
    private val treeRepository: TreeRepository,
) : Step<CreateFileBlobsIfNotExistOutput, OutputCreateNewTree> {
    override fun execute(input: CreateFileBlobsIfNotExistOutput): OutputCreateNewTree {
        val mergedBlobFiles = getMergedBlobFiles(input.fileBlobList)

        val treeId = treeRepository.create(mergedBlobFiles)

        return OutputCreateNewTree(
            treeId = treeId,
        )
    }

    private fun getMergedBlobFiles(fileBlobList: List<FileBlob>): List<FileBlob> {
        val head = headRepository.getHead() ?: return fileBlobList

        val currentCommit = commitRepository.get(head)
        val currentTree = treeRepository.get(currentCommit.treeId)

        return currentTree.fileBlobList.overrideWith(fileBlobList)
    }
}