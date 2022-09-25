package repository.index

interface IndexRepository {
    fun add(stagedFiles: List<String>)
}