package service.commit

interface CommitService {
    fun run(stagedFiles: List<Path>)
}