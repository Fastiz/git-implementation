package repository.blob

import model.FileBlobId

typealias Path = String

interface FileBlobRepository {
    fun createIfNotExists(path: Path): FileBlobId
}