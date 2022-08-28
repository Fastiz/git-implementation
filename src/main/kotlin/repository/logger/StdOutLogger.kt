package repository.logger

class StdOutLogger : Logger {
    override fun println(line: String) {
        println(line)
    }
}