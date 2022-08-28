package service.commit.step

import model.FileBlob
import model.Step
import repository.blob.FileBlobRepository
import repository.blob.FileBlobRepositoryImpl

data class CreateFileBlobsIfNotExistInput(
    val stagedFiles: List<String>
)

data class CreateFileBlobsIfNotExistOutput(
    val fileBlobList: List<FileBlob>,
)

class CreateFileBlobsIfNotExist(
    private val fileBlobRepository: FileBlobRepository = FileBlobRepositoryImpl()
) : Step<CreateFileBlobsIfNotExistInput, CreateFileBlobsIfNotExistOutput> {

    override fun execute(input: CreateFileBlobsIfNotExistInput): CreateFileBlobsIfNotExistOutput {
        val fileBlobList = input.stagedFiles.map {
            val id = fileBlobRepository.createIfNotExists(it)

            FileBlob(id = id, path = it)
        }

        return CreateFileBlobsIfNotExistOutput(
            fileBlobList = fileBlobList,
        )
    }
}