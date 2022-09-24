package repository.head

import model.CommitId

interface HeadRepository {
    fun getHead(): CommitId?

    fun setHead(commitId: CommitId)
}
