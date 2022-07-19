package dao.objects.head

interface HeadDao {
    fun getHead(): Hash

    fun setHead(commitId: String)
}