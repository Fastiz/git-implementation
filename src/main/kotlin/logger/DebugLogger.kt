package logger

class DebugLogger : Logger {
    override fun print(line: String) {
        println("[ info\t]: $line")
    }

    override fun printDebug(line: String) {
        println("[ debug\t]: $line")
    }
}
