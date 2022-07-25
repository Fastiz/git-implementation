package repository.blob

import dao.objects.files.FileDao
import dao.objects.files.FileDaoImpl
import dao.objects.objects.ObjectsDao
import dao.objects.objects.ObjectsDaoImpl

class FileBlobRepositoryImpl(
    private val fileDao: FileDao = FileDaoImpl(),
    private val objectsDao: ObjectsDao = ObjectsDaoImpl()
) : FileBlobRepository {
    override fun create(path: Path): Hash {
        val lines = fileDao.readFile(path) {
            generateSequence { readLine() }
        }

        return objectsDao.createFromString(lines.joinToString("\n"))
    }
}