package model

data class FileBlobId(val value: String) {
    companion object {
        fun from(value: String) = FileBlobId(value)
    }
}

data class FileBlob(val path: String, val id: FileBlobId)