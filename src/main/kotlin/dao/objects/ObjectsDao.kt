package dao.objects

typealias Hash = String

interface ObjectsDao {
    fun createFromString(content: String): Hash

    fun createFromPath(path: String): Hash

    fun get(id: Hash): String
}
