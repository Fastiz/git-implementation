package service.commit.step

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.CommitDataProvider.buildCommit
import model.FileBlobDataProvider.buildFileBlob
import model.TreeDataProvider.buildTree
import org.junit.Before
import org.junit.Test
import repository.commit.CommitRepository
import repository.head.HeadRepository
import repository.tree.TreeRepository
import kotlin.test.assertEquals

class CreateNewTreeTest {
    private lateinit var treeRepository: TreeRepository
    private lateinit var commitRepository: CommitRepository
    private lateinit var headRepository: HeadRepository
    private lateinit var createNewTree: CreateNewTree

    @Before
    fun before() {
        treeRepository = mockk()
        commitRepository = mockk()
        headRepository = mockk()
        createNewTree = CreateNewTree(
            commitRepository = commitRepository,
            headRepository = headRepository,
            treeRepository = treeRepository
        )
    }

    @Test
    fun `merges content from trees and calls the tree repository`() {
        TODO()
    }
}