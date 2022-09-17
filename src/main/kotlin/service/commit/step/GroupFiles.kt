package service.commit.step

import model.Directory
import model.File
import model.FileBlob
import model.FileTreeEntry
import model.SubtreeTreeEntry
import model.Tree

object GroupFiles {
    fun groupFilesByFolder(fileBlobList: List<FileBlob>): Map<String, List<FileBlob>> {
        val result = mutableMapOf<String, List<FileBlob>>()

        fileBlobList.forEach {
            val directoryPath = it.path.replace("/[^/]+$".toRegex(), "")

            val filesInTheSameDirectory = result[directoryPath] ?: emptyList()

            result[directoryPath] = filesInTheSameDirectory + it
        }

        return result
    }

    fun groupFilesByFolderFromTree(
        tree: Tree,
        treeProvider: (treeId: String) -> Tree,
    ): Map<String, List<FileBlob>> {
        return groupFilesByFolderFromTreeRec(
            currentPath = Directory.ROOT.path,
            currentTree = tree,
            treeProvider = treeProvider,
        )
    }

    private fun groupFilesByFolderFromTreeRec(
        currentPath: String,
        currentTree: Tree,
        treeProvider: (treeId: String) -> Tree,
    ): Map<String, List<FileBlob>> {
        val result = mutableMapOf<String, List<FileBlob>>()
        val fileBlobList = mutableListOf<FileBlob>()

        currentTree.entries.forEach {
            when (it) {
                is FileTreeEntry -> {
                    fileBlobList.add(FileBlob(path = it.path, id = it.fileBlobId))
                }
                is SubtreeTreeEntry -> {
                    val subtree = treeProvider(it.subtreeId)
                    val subtreeResult = groupFilesByFolderFromTreeRec(
                        currentPath = it.path,
                        currentTree = subtree,
                        treeProvider = treeProvider,
                    )

                    result.putAll(subtreeResult)
                }
            }
        }

        result[currentPath] = fileBlobList

        return result
    }

    fun mergeGroupedFiles(
        base: Map<String, List<FileBlob>>,
        override: Map<String, List<FileBlob>>
    ): Map<String, List<FileBlob>> {
        TODO()
    }

}