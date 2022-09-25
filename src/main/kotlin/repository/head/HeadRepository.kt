package repository.head

import model.CommitId

interface HeadRepository {
    fun getHead(): CommitId?

    fun setDetachedHead(commitId: CommitId)

    fun setHead(ref: String)
}
