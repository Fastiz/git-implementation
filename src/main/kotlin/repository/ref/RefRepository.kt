package repository.ref

import model.CommitId

interface RefRepository {
    fun create(refName: String, commitId: CommitId)
    fun get(refName: String): CommitId?
}