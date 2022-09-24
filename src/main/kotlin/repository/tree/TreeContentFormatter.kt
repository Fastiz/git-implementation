package repository.tree

import model.FileBlobId
import model.FileTreeEntry
import model.SubtreeTreeEntry
import model.Tree
import model.TreeId
import model.TreeInput

object TreeContentFormatter {
    fun formatTreeContent(treeInput: TreeInput): String {
        val sb = StringBuilder()

        treeInput.entries.forEach {
            when (it) {
                is FileTreeEntry -> sb.appendLine("blob ${it.fileBlobId.value} ${it.path}")
                is SubtreeTreeEntry -> sb.appendLine("tree ${it.subtreeId.value} ${it.path}")
            }
        }

        return sb.toString()
    }

    fun mapTreeFromBlob(id: TreeId, blob: String): Tree {
        val lines = blob.split("\n")

        val entries = lines.mapNotNull {
            val values = it.split(" ")

            if (values.size != 3) {
                return@mapNotNull null
            }

            val (type, entityId, entityPath) = values

            when (type) {
                "blob" -> FileTreeEntry(
                    fileBlobId = FileBlobId.from(entityId),
                    path = entityPath
                )
                "tree" -> SubtreeTreeEntry(
                    subtreeId = TreeId.from(entityId),
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
