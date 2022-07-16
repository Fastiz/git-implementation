package repository.refs

import model.Commit

interface RefRepository {
    fun getHead(): Commit

    fun setHead(commitId: String)
}