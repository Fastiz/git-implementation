package dao.objects.objects

import java.io.File
import java.security.MessageDigest
import java.util.stream.Collectors
import java.util.stream.Stream

class ObjectsDaoImpl : ObjectsDao {
    override fun createFromString(content: String): Hash {
        val hash = MessageDigest
            .getInstance("SHA-256")
            .digest(content.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }

        val file = fileInObjects(hash)

        file.createNewFile()

        file.writeText(content)

        return hash
    }

    override fun get(id: Hash): String {
        val lines: Stream<String> = fileInObjects(id)
            .inputStream()
            .bufferedReader()
            .lines()

        return lines
            .collect(Collectors.toList())
            .joinToString { "\n" }
    }

    companion object {
        private const val OBJECTS_PATH = ".git-fastiz/objects"

        fun fileInObjects(path: String) = File("$OBJECTS_PATH/$path")
    }
}