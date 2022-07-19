package dao.objects.head

import java.io.File

typealias Hash = String

class HeadDaoImpl : HeadDao {
    override fun getHead(): Hash {
        val inputStream = File(HEAD_PATH).inputStream()

        val line = inputStream.bufferedReader().lines().findFirst().orElseThrow { Exception("could not read HEAD") }

        return line.replace("ref: ", "")
    }

    override fun setHead(commitId: String) {
        File(HEAD_PATH).printWriter().use { }
    }

    companion object {
        const val HEAD_PATH = ".git-fastiz/HEAD"
    }
}