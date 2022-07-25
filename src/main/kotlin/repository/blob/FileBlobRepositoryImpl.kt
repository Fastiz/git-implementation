package repository.blob

import dao.objects.objects.ObjectsDao
import dao.objects.objects.ObjectsDaoImpl
import java.io.File

class FileBlobRepositoryImpl(
    private val objectsDao: ObjectsDao = ObjectsDaoImpl()
) : FileBlobRepository {
    override fun create(path: Path): Hash {
        val inputStream = File(path).inputStream()
        val sb = StringBuilder()

        inputStream.bufferedReader().forEachLine { sb.append(it) }

        return objectsDao.createFromString(sb.toString())
    }
}