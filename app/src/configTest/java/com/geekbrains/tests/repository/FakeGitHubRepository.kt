package com.geekbrains.tests.repository

import com.afrosin.apptests.model.SearchResponse
import com.afrosin.apptests.model.SearchResult
import com.afrosin.apptests.presenter.RepositoryContract
import io.reactivex.Observable
import retrofit2.Response
import kotlin.random.Random

internal class FakeGitHubRepository : RepositoryContract {

    override fun searchGithub(
        query: String,
        callback: RepositoryCallback
    ) {
        callback.handleGitHubResponse(Response.success(getFakeResponce()))
    }

    override fun searchGithub(query: String): Observable<SearchResponse> {
        return Observable.just(getFakeResponce())
    }

    private fun getFakeResponce(): SearchResponse {
        val list: MutableList<SearchResult> = mutableListOf()

        for (index in 1..100) {
            list.add(
                SearchResult(
                    id = index,
                    name = "Name: $index",
                    fullName = "FullName: $index",
                    private = Random.nextBoolean(),
                    description = "Description: $index",
                    updatedAt = "Updated: $index",
                    size = index,
                    stargazersCount = Random.nextInt(100),
                    language = "",
                    hasWiki = Random.nextBoolean(),
                    archived = Random.nextBoolean(),
                    score = index.toDouble()
                )
            )
        }

        return SearchResponse(list.size, list)
    }

    override suspend fun searchGithubAsync(query: String): SearchResponse {
        return getFakeResponce()
    }
}