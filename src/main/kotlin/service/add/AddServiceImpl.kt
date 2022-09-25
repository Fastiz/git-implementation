package service.add

import repository.index.IndexRepository

class AddServiceImpl(
    private val indexRepository: IndexRepository
) : AddService {
    override fun run(stagedFiles: List<String>) {
        indexRepository.add(stagedFiles)
    }
}