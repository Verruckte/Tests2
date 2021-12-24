package com.geekbrains.tests.repository

import com.geekbrains.tests.presenter.RepositoryContract

class RepositoryContractFactory {
    fun createRepository(): RepositoryContract {
        return FakeGitHubRepository()
    }
}