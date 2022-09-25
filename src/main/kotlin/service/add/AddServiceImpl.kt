package service.add

import model.FileBlob
import repository.blob.FileBlobRepository
import repository.index.IndexRepository

class AddServiceImpl(
    private val fileBlobRepository: FileBlobRepository,
    private val indexRepository: IndexRepository
) : AddService {
    override fun run(stagedFiles: List<String>) {
        val blobList = stagedFiles.map {
            val fileBlobId = fileBlobRepository.createIfNotExists(it)
            FileBlob(path = it, id = fileBlobId)
        }

        indexRepository.add(blobList)
    }
}