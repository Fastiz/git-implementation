package repository.tree

import model.FileBlob
import model.Tree

object TreeContentFormatter {
    fun formatTreeContent(fileBlobList: List<FileBlob>): String {
        val sb = StringBuilder()

        fileBlobList.forEach {
            sb.appendLine("blob ${it.id} ${it.path}")
        }

        return sb.toString()
    }

    fun mapTreeFromBlob(id: String, blob: String): Tree {
        val lines = blob.split("\n")

        val fileBlobList = lines.mapNotNull {
            val values = it.split(" ")

            if(values.size != 3) {
                return@mapNotNull null
            }

            FileBlob(
                id = values[1],
                path = values[2]
            )
        }

        return Tree(
            id = id,
            fileBlobList = fileBlobList
        )
    }
}