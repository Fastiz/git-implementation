package repository.index

import model.FileBlob

interface IndexRepository {
    fun add(stagedBlobs: Iterable<FileBlob>)

    fun set(stagedBlobs: Iterable<FileBlob>)
}
