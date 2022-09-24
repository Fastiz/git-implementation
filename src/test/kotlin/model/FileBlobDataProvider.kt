package model

object FileBlobDataProvider {
    fun buildFileBlob(
        id: FileBlobId = FileBlobId.from("file-blob-id"),
        path: String = "/example"
    ): FileBlob {
        return FileBlob(
            id = id,
            path = path
        )
    }
}
