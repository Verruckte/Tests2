package com.geekbrains.tests.repository

import com.geekbrains.tests.presenter.RepositoryContract
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryContractFactory {

    private val baseURL = "https://api.github.com"

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createRepository(): RepositoryContract {
        return GitHubRepository(createRetrofit().create(GitHubApi::class.java))
    }
}