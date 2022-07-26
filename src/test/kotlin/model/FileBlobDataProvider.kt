package model

object FileBlobDataProvider {
    fun buildFileBlob(
        id: String = "file-blob-id",
        path: String = "/example"
    ): FileBlob {
        return FileBlob(
            id = id,
            path = path
        )
    }
}
