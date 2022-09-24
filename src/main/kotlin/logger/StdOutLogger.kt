package logger

class StdOutLogger : Logger {
    override fun println(line: String) {
        kotlin.io.println(line)
    }
}