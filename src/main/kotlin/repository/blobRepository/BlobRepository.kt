package repository.blobRepository

typealias Path = String
typealias Hash = String

interface BlobRepository {
    fun createFromString(content: String): Hash

    fun createFromFile(path: Path): Hash

    fun get(id: Hash): String
}