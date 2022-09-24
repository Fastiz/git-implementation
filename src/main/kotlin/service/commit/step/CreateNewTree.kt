package service.commit.step

import logger.Logger
import model.CommitId
import model.Directory
import model.FileBlob
import model.FileTreeEntry
import model.Step
import model.SubtreeTreeEntry
import model.TreeId
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
    val treeId: TreeId,
)

class CreateNewTree(
    private val commitRepository: CommitRepository,
    private val headRepository: HeadRepository,
    private val treeRepository: TreeRepository,
    private val logger: Logger,
) : Step<CreateFileBlobsIfNotExistOutput, OutputCreateNewTree> {
    override fun execute(input: CreateFileBlobsIfNotExistOutput): OutputCreateNewTree {
        val stagingTreeGroupedFiles = groupFilesByFolder(input.fileBlobList)

        logger.printDebug("CreateNewTree - staging grouped files to include in tree:")
        stagingTreeGroupedFiles.forEach { logger.printDebug("\t$it") }

        val currentCommitId = headRepository.getHead()
        val currentTreeGroupedFiles = currentCommitId?.let(::getGroupedFilesFromCurrentTree) ?: emptyMap()

        logger.printDebug("CreateNewTree - current tree grouped files to include in tree:")
        currentTreeGroupedFiles.forEach { logger.printDebug("\t$it") }

        val mergedGroupedFiles = mergeGroupedFiles(
            base = currentTreeGroupedFiles,
            override = stagingTreeGroupedFiles
        )

        logger.printDebug("CreateNewTree - merged grouped files to include in tree:")
        mergedGroupedFiles.forEach { logger.printDebug("\t$it") }

        val treeId = createTreesFromGroupedFiles(mergedGroupedFiles)

        logger.printDebug("CreateNewTree - created root tree ($treeId)")

        return OutputCreateNewTree(treeId = treeId)
    }

    private fun getGroupedFilesFromCurrentTree(currentCommitId: CommitId): Map<String, List<FileBlob>> {
        val currentCommit = commitRepository.get(currentCommitId)

        val currentTree = treeRepository.get(currentCommit.treeId)

        return groupFilesByFolderFromTree(currentTree) { treeId ->
            treeRepository.get(treeId)
        }
    }

    private fun createTreesFromGroupedFiles(groupedFiles: Map<String, List<FileBlob>>): TreeId {
        val allDirectories = getAllDirectories(groupedFiles.keys.toList())
        val createdTrees = mutableMapOf<String, TreeId>()

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
        createdTrees: MutableMap<String, TreeId>,
    ): TreeId {
        val children = getChildrenDirectories(currentDirectory, allDirectories)

        val subtreeEntries = children.map { child ->
            val subtreeId = createdTrees[child]
                ?: createTreesFromGroupedFilesRec(
                    currentDirectory = child,
                    allDirectories = allDirectories,
                    groupedFiles = groupedFiles,
                    createdTrees = createdTrees
                )

            SubtreeTreeEntry(path = child, subtreeId = subtreeId)
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
