package repository.index

import dao.files.FileDao
import directory.Index
import logger.Logger
import logger.util.FileBlob.debugFileBlobs
import model.FileBlob
import repository.index.IndexLineFormatter.fileBlobToLine
import repository.index.IndexLineFormatter.parseLineToBlob

class IndexRepositoryImpl(
    private val index: Index,
    private val fileDao: FileDao,
    private val logger: Logger,
) : IndexRepository {
    override fun add(stagedBlobs: Iterable<FileBlob>) {
        logger.printDebug("IndexRepositoryImpl")

        val currentIndexLines = fileDao.readFile(index.path) {
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

        fileDao.writeFile(index.path) {
            result
                .map(::fileBlobToLine)
                .forEach(::writeLine)
        }
    }

    override fun set(stagedBlobs: Iterable<FileBlob>) {
        fileDao.writeFile(index.path) {
            stagedBlobs
                .map(::fileBlobToLine)
                .forEach(::writeLine)
        }
    }

    override fun get(): Sequence<FileBlob> {
        val currentIndexLines = fileDao.readFile(index.path) {
            readAllLines()
        }

        return currentIndexLines
            .map { parseLineToBlob(it) }
    }
}
