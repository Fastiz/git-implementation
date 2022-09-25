package repository.index

import dao.files.FileDao
import logger.Logger
import model.File
import model.FileBlob
import repository.index.IndexLineFormatter.fileBlobToLine
import repository.index.IndexLineFormatter.parseLineToBlob

class IndexRepositoryImpl(
    private val fileDao: FileDao,
    private val logger: Logger,
) : IndexRepository {
    override fun add(stagedBlobs: List<FileBlob>) {
        logger.printDebug("IndexRepositoryImpl")

        val currentIndexLines = fileDao.readFile(File.INDEX.path) {
            readAllLines()
        }

        val indexedBlobsMap = currentIndexLines
            .map { parseLineToBlob(it) }
            .associateBy({ it.path }, { it })

        logger.printDebug("Indexed blobs")
        logger.debugFileBlobs(indexedBlobsMap.entries.map { it.value })

        val stagedBlobsMap = stagedBlobs
            .associateBy({ it.path }, { it })

        logger.printDebug("Staged blobs")
        logger.debugFileBlobs(stagedBlobsMap.entries.map { it.value })

        val result = (indexedBlobsMap + stagedBlobsMap)
            .toList()
            .map { it.second }
            .sortedBy { "${it.id.value}${it.path}" }

        logger.printDebug("Merged blobs")
        logger.debugFileBlobs(result)

        fileDao.writeFile(File.INDEX.path) {
            result
                .map(::fileBlobToLine)
                .forEach(::writeLine)
        }
    }

    private fun Logger.debugFileBlobs(fileBlobs: Iterable<FileBlob>) {
        fileBlobs.forEach { fileBlob ->
            printDebug("\t${fileBlob.id.value} ${fileBlob.path}")
        }
    }
}
