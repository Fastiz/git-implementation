package repository.blob

import dao.files.FileDao
import dao.objects.ObjectsDao

class FileBlobRepositoryImpl(
    private val fileDao: FileDao,
    private val objectsDao: ObjectsDao
) : FileBlobRepository {
    override fun createIfNotExists(path: Path): Hash {
        val lines = fileDao.readFile(path) {
            generateSequence { readLine() }
        }

        return objectsDao.createFromString(lines.joinToString("\n"))
    }
}