package logger

class StdOutLogger : Logger {
    override fun print(line: String) {
        println(line)
    }

    override fun printDebug(line: String) {}
}
