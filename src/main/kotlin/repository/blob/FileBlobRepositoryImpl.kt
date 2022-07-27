package repository.blob

import dao.files.FileDao
import dao.files.FileDaoImpl
import dao.objects.ObjectsDao
import dao.objects.ObjectsDaoImpl

class FileBlobRepositoryImpl(
    private val fileDao: FileDao = FileDaoImpl(),
    private val objectsDao: ObjectsDao = ObjectsDaoImpl()
) : FileBlobRepository {
    override fun createIfNotExists(path: Path): Hash {
        val lines = fileDao.readFile(path) {
            generateSequence { readLine() }
        }

        return objectsDao.createFromString(lines.joinToString("\n"))
    }
}