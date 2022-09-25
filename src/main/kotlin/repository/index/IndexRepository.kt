package repository.index

import model.FileBlob

interface IndexRepository {
    fun add(stagedBlobs: List<FileBlob>)
}