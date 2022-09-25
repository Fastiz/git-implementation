package repository.index

import dao.files.FileDao
import model.File
import model.FileBlob
import repository.index.IndexLineFormatter.fileBlobToLine
import repository.index.IndexLineFormatter.parseLineToBlob

class IndexRepositoryImpl(
    private val fileDao: FileDao
) : IndexRepository {
    override fun add(stagedBlobs: List<FileBlob>) {
        val currentIndexLines = fileDao.readFile(File.INDEX.path) {
            readAllLines()
        }

        val indexedBlobsMap = currentIndexLines
            .map { parseLineToBlob(it) }
            .associateBy({ it.id }, { it })

        val stagedBlobsMap = stagedBlobs
            .associateBy({ it.id }, { it })

        val result = (indexedBlobsMap + stagedBlobsMap)
            .toList()
            .map { it.second }
            .sortedBy { it.id.value }

        fileDao.writeFile(File.INDEX.path) {
            result
                .map(::fileBlobToLine)
                .forEach(::writeLine)
        }
    }
}
