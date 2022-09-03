package dao

import dao.files.FileDao
import dao.files.FileDaoImpl
import dao.head.HeadDao
import dao.head.HeadDaoImpl
import dao.objects.ObjectsDao
import dao.objects.ObjectsDaoImpl
import org.koin.dsl.module

object DaoModule {
    val module = module {
        single<FileDao> { FileDaoImpl() }
        // TODO: this dependency shouldn't exist
        single<HeadDao> { HeadDaoImpl(get()) }
        single<ObjectsDao> { ObjectsDaoImpl() }
    }
}