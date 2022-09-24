package dao.files

import java.util.stream.Stream

class ReaderImpl(lines: Stream<String>) : Reader {
    private val iterator: Iterator<String> = lines.iterator()

    override fun readLine(): String? {
        if (iterator.hasNext()) {
            return iterator.next()
        }

        return null
    }
}
