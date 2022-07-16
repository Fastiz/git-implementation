package dao.objects

typealias Path = String
typealias Hash = String

interface ObjectsDao {
    fun createFromString(content: String): Hash

    fun createFromFile(path: Path): Hash

    fun get(id: Hash): String
}