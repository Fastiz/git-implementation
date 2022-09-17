package service.commit.step

import model.FileBlob
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
        treeProvider: (treeId: String) -> Tree
    ): Map<String, List<FileBlob>> {
        TODO()
    }

    fun mergeGroupedFiles(
        base: Map<String, List<FileBlob>>,
        override: Map<String, List<FileBlob>>
    ): Map<String, List<FileBlob>> {
        TODO()
    }

}