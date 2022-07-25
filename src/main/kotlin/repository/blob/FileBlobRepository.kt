package repository.blob

typealias Path = String
typealias Hash = String

interface FileBlobRepository {
    fun createIfNotExists(path: Path): Hash
}