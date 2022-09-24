package service.commit.step

import logger.Logger
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
    private val logger: Logger,
) : Step<CreateFileBlobsIfNotExistOutput, OutputCreateNewTree> {
    override fun execute(input: CreateFileBlobsIfNotExistOutput): OutputCreateNewTree {
        val stagingTreeGroupedFiles = groupFilesByFolder(input.fileBlobList)

        val currentCommitId = headRepository.getHead()
        val currentTreeGroupedFiles = currentCommitId?.let(::getGroupedFilesFromCurrentTree) ?: emptyMap()

        val mergedGroupedFiles = mergeGroupedFiles(
            base = currentTreeGroupedFiles,
            override = stagingTreeGroupedFiles
        )

        logger.printDebug("CreateNewTree - merged group files to include in tree: $mergedGroupedFiles")

        val treeId = createTreesFromGroupedFiles(mergedGroupedFiles)

        logger.printDebug("CreateNewTree - created root tree ($treeId)")

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

        return createTreesFromGroupedFilesRec(
            currentDirectory = Directory.ROOT.path,
            allDirectories = allDirectories,
            groupedFiles = groupedFiles,
            createdTrees = createdTrees
        )
    }

    private fun createTreesFromGroupedFilesRec(
        currentDirectory: String,
        allDirectories: Set<String>,
        groupedFiles: Map<String, List<FileBlob>>,
        createdTrees: MutableMap<String, String>,
    ): String {
        val children = getChildrenDirectories(currentDirectory, allDirectories)

        val subtreeEntries = children.map { child ->
            val subtreeId = createdTrees[child]
                ?: createTreesFromGroupedFilesRec(
                    currentDirectory = child,
                    allDirectories = allDirectories,
                    groupedFiles = groupedFiles,
                    createdTrees = createdTrees
                )

            SubtreeTreeEntry(path = currentDirectory, subtreeId = subtreeId)
        }

        val fileEntries = (groupedFiles[currentDirectory] ?: emptyList())
            .map { FileTreeEntry(path = it.path, fileBlobId = it.id) }

        val entries = fileEntries + subtreeEntries

        val treeInput = TreeInput(entries = entries)

        logger.printDebug("CreateNewTree - creating tree with the following input: $treeInput")

        val treeId = treeRepository.create(treeInput)

        createdTrees[currentDirectory] = treeId

        return treeId
    }
}