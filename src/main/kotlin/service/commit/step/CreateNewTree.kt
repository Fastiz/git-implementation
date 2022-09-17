package service.commit.step

import model.FileBlob
import model.Step
import model.Tree
import model.overrideWith
import repository.commit.CommitRepository
import repository.head.HeadRepository
import repository.tree.TreeRepository
import service.commit.step.GroupFiles.groupFilesByFolder
import service.commit.step.GroupFiles.groupFilesByFolderFromTree
import service.commit.step.GroupFiles.mergeGroupedFiles

data class OutputCreateNewTree(
    val treeId: String,
)

class CreateNewTree(
    private val commitRepository: CommitRepository,
    private val headRepository: HeadRepository,
    private val treeRepository: TreeRepository,
) : Step<CreateFileBlobsIfNotExistOutput, OutputCreateNewTree> {
    override fun execute(input: CreateFileBlobsIfNotExistOutput): OutputCreateNewTree {
        val stagingTreeGroupedFiles = groupFilesByFolder(input.fileBlobList)

        val currentCommitId = headRepository.getHead()
        val currentTreeGroupedFiles = currentCommitId?.let(::getGroupedFilesFromCurrentTree) ?: emptyMap()

        val mergedGroupedFiles = mergeGroupedFiles(
            base = currentTreeGroupedFiles,
            override = stagingTreeGroupedFiles
        )

        val treeId = createTreesFromGroupedFiles(mergedGroupedFiles)

        return OutputCreateNewTree(treeId = treeId)
    }

    private fun getGroupedFilesFromCurrentTree(currentCommitId: String): Map<String, List<FileBlob>> {
        val currentCommit = commitRepository.get(currentCommitId)

        val currentTree = treeRepository.get(currentCommit.treeId)

        return groupFilesByFolderFromTree(currentTree) { treeId ->
            treeRepository.get(treeId)
        }
    }

    private fun createTreesFromGroupedFiles(groupedFiles: Map<String, List<FileBlob>>): String {
        TODO()
    }
}