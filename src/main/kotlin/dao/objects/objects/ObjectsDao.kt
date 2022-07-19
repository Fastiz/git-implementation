package dao.objects.objects

typealias Hash = String

interface ObjectsDao {
    fun createFromString(content: String): Hash

    fun get(id: Hash): String
}