package service.commit.step

import logger.Logger
import model.FileBlob
import model.Step
import repository.blob.FileBlobRepository

data class CreateFileBlobsIfNotExistInput(
    val stagedFiles: List<String>
)

data class CreateFileBlobsIfNotExistOutput(
    val fileBlobList: List<FileBlob>,
)

class CreateFileBlobsIfNotExist(
    private val fileBlobRepository: FileBlobRepository,
    private val logger: Logger,
) : Step<CreateFileBlobsIfNotExistInput, CreateFileBlobsIfNotExistOutput> {

    override fun execute(input: CreateFileBlobsIfNotExistInput): CreateFileBlobsIfNotExistOutput {
        logger.printDebug("CreateFileBlobsIfNotExist - received ${input.stagedFiles.size} files to create if they don't exist")

        val fileBlobList = input.stagedFiles.map {
            val id = fileBlobRepository.createIfNotExists(it)

            FileBlob(id = id, path = it)
        }

        return CreateFileBlobsIfNotExistOutput(
            fileBlobList = fileBlobList,
        )
    }
}
