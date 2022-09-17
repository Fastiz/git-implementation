package repository.tree

import model.FileTreeEntry
import model.SubtreeTreeEntry
import model.Tree
import model.TreeInput

object TreeContentFormatter {
    fun formatTreeContent(treeInput: TreeInput): String {
        val sb = StringBuilder()

        treeInput.entries.forEach {
            when (it) {
                is FileTreeEntry -> sb.appendLine("blob ${it.fileBlobId} ${it.path}")
                is SubtreeTreeEntry -> sb.appendLine("tree ${it.subtreeId} ${it.path}")
            }
        }

        return sb.toString()
    }

    fun mapTreeFromBlob(id: String, blob: String): Tree {
        val lines = blob.split("\n")

        val entries = lines.mapNotNull {
            val values = it.split(" ")

            if (values.size != 3) {
                return@mapNotNull null
            }

            val (type, entityId, entityPath) = values

            when (type) {
                "blob" -> FileTreeEntry(
                    fileBlobId = entityId,
                    path = entityPath
                )
                "tree" -> SubtreeTreeEntry(
                    subtreeId = entityId,
                    path = entityPath
                )
                else -> {
                    throw Exception("Invalid entry type for tree")
                }
            }
        }

        return Tree(
            id = id,
            entries = entries
        )
    }
}