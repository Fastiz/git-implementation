package repository.blob

typealias Path = String
typealias Hash = String

interface FileBlobRepository {
    fun create(path: Path): Hash
}