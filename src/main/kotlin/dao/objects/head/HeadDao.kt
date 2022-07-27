package dao.objects.head

interface HeadDao {
    fun getHead(): String

    fun setHead(commitId: String)
}