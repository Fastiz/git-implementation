package repository.refs

typealias Hash = String

interface HeadRepository {
    fun getHead(): Hash

    fun setHead(commitId: String)
}