package service.commit.step

import directory.Root
import logger.Logger
import logger.util.FileBlob.debugFileBlobs
import model.FileBlob
import model.FileTreeEntry
import model.Step
import model.SubtreeTreeEntry
import model.TreeId
import model.TreeInput
import repository.tree.TreeRepository
import service.commit.step.DirectoriesParser.getAllDirectories
import service.commit.step.DirectoriesParser.getDirectChildrenDirectories
import service.commit.step.GroupFiles.groupFilesByFolder

data class OutputCreateNewTree(
    val treeId: TreeId,
)

class CreateNewTree(
    private val root: Root,
    private val treeRepository: TreeRepository,
    private val logger: Logger,
) : Step<Iterable<FileBlob>, OutputCreateNewTree> {
    override fun execute(input: Iterable<FileBlob>): OutputCreateNewTree {
        logger.printDebug("CreateNewTree")

        val groupedFiles = groupFilesByFolder(input.asIterable())

        logger.printDebug("Grouped files")
        groupedFiles.entries.forEach { (directory, fileBlobs) ->
            logger.printDebug("- Directory $directory")
            logger.debugFileBlobs(fileBlobs)
        }

        val treeId = createTreesFromGroupedFiles(groupedFiles)

        logger.printDebug("Created root tree ($treeId)")

        return OutputCreateNewTree(treeId = treeId)
    }

    private fun createTreesFromGroupedFiles(groupedFiles: Map<String, List<FileBlob>>): TreeId {
        val allDirectories = getAllDirectories(groupedFiles.keys.toList())
        val createdTrees = mutableMapOf<String, TreeId>()

        return createTreesFromGroupedFilesRec(
            currentDirectory = "",
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
        val children = getDirectChildrenDirectories(currentDirectory, allDirectories)

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

        logger.printDebug("Creating tree with the following input: $treeInput")

        val treeId = treeRepository.create(treeInput)

        createdTrees[currentDirectory] = treeId

        return treeId
    }
}
