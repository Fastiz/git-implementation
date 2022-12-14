package dao

import dao.files.FileDao
import dao.files.FileDaoImpl
import dao.objects.ObjectsDao
import dao.objects.ObjectsDaoImpl
import org.koin.dsl.module

object DaoModule {
    val module = module {
        single<FileDao> { FileDaoImpl(root = get()) }
        single<ObjectsDao> { ObjectsDaoImpl(objects = get(), fileDao = get()) }
    }
}
