package dao.objects.objects

import dao.objects.files.FileDao
import dao.objects.files.FileDaoImpl
import java.security.MessageDigest

class ObjectsDaoImpl(private val fileDao: FileDao = FileDaoImpl()) : ObjectsDao {
    override fun createFromString(content: String): Hash {
        val hash = MessageDigest
            .getInstance("SHA-256")
            .digest(content.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }

        val path = pathInObjects(hash)

        fileDao.createFile(path)
        fileDao.writeFile(path) {
            write(content)
        }

        return hash
    }

    override fun get(id: Hash): String {
        val lines = fileDao.readFile(pathInObjects(id)) {
            generateSequence { readLine() }
        }

        return lines.joinToString("\n")
    }

    companion object {
        private const val OBJECTS_PATH = ".git-fastiz/objects"

        fun pathInObjects(path: String) = "$OBJECTS_PATH/$path"
    }
}