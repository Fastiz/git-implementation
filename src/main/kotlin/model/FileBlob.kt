package model

data class FileBlob(val path: String, val id: String)

fun List<FileBlob>.overrideWith(fileBlobList: List<FileBlob>): List<FileBlob> {
    val result = fileBlobList.toMutableList()

    val pathList = fileBlobList.map { it.path }

    forEach {
        if(!pathList.contains(it.path)){
            result.add(it)
        }
    }

    return result.sortedBy { it.path }
}