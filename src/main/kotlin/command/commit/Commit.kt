package command.commit

interface Commit {
    fun run(pathList: List<Path>)
}