package com.example.servicesandbrexample

import com.example.servicesandbrexample.model.repository.Repository
import com.example.servicesandbrexample.model.repository.RepositoryImpl
import com.example.servicesandbrexample.ui.vm.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Repository> { RepositoryImpl() }

    viewModel { MainViewModel(get()) }
}