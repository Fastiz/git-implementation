package repository.ref

import model.CommitId

interface RefRepository {
    fun get(refName: String): CommitId?
    fun set(refName: String, commitId: CommitId)
}