package service.commit.step

import model.Directory
import model.FileBlob
import model.FileTreeEntry
import model.Step
import model.SubtreeTreeEntry
import model.TreeInput
import repository.commit.CommitRepository
import repository.head.HeadRepository
import repository.tree.TreeRepository
import service.commit.step.DirectoriesParser.getAllDirectories
import service.commit.step.DirectoriesParser.getChildrenDirectories
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
        val allDirectories = getAllDirectories(groupedFiles.keys.toList())

        val createdTrees = mutableMapOf<String, String>()
        allDirectories.sortedBy { it.length }.forEach { directory ->
            val children = getChildrenDirectories(directory, allDirectories)

            val subtreeEntries = children.map { child ->
                val subtreeId = createdTrees[child] ?: throw IllegalStateException()

                SubtreeTreeEntry(path = directory, subtreeId = subtreeId)
            }

            val fileEntries = (groupedFiles[directory] ?: throw IllegalStateException())
                .map { FileTreeEntry(path = it.path, fileBlobId = it.id) }

            val entries = fileEntries + subtreeEntries

            val treeInput = TreeInput(entries = entries)

            val treeId = treeRepository.create(treeInput)

            createdTrees[directory] = treeId
        }

        return createdTrees[Directory.ROOT.path] ?: throw IllegalStateException()
    }
}