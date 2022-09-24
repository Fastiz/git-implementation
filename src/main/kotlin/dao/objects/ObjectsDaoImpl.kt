package dao.objects

import dao.files.FileDao
import dao.files.FileDaoImpl
import model.Directory
import model.extendPath
import java.security.MessageDigest

class ObjectsDaoImpl(private val fileDao: FileDao = FileDaoImpl()) : ObjectsDao {
    override fun createFromString(content: String): Hash {
        val hash = MessageDigest
            .getInstance("SHA-256")
            .digest(content.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }

        val path = Directory.OBJECTS.extendPath(hash)

        if (!fileDao.doesFileExist(path)) {
            fileDao.createFile(path)
            fileDao.writeFile(path) {
                write(content)
            }
        }

        return hash
    }

    override fun get(id: Hash): String {
        val lines = fileDao.readFile(Directory.OBJECTS.extendPath(id)) {
            generateSequence { readLine() }
        }

        return lines.joinToString("\n")
    }
}
