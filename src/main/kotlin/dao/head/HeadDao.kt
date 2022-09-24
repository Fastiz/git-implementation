package dao.head

interface HeadDao {
    fun getHead(): String?

    fun setHead(commitId: String)
}
