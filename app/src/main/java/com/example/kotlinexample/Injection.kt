package com.example.kotlinexample

/*
object Injection {

    private fun getSearchService(): SearchService = SampleRetrofit.create(SearchService::class)

    private fun getUserService(): UserService = SampleRetrofit.create(UserService::class)

    private fun getSearchDao(context: Context): RepositoryDao =
        SampleDatabase.getInstance(context).searchDao()

    private fun getSearchRemoteDataSource(): SearchRemoteDataSource =
        SearchRemoteDataSource(getSearchService())

    private fun getUserRemoteDataSource(): UserRemoteDataSource =
        UserRemoteDataSource(getUserService())

    fun getSearchRepository(context: Context): SearchRepository = SearchRepository(
        remote = getSearchRemoteDataSource(),
        repoDao = getSearchDao(context)
    )

    fun getUserRepository(): UserRepository = UserRepository(remote = getUserRemoteDataSource())

    @Suppress("UNCHECKED_CAST")
    fun provideMainViewModelFactory(context: Context): ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                MainViewModel(
                    schedulerProvider = SchedulerProvider,
                    searchRepository = getSearchRepository(context)
                ) as T
        }

    @Suppress("UNCHECKED_CAST")
    fun provideSearchViewModelFactory(context: Context): ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                SearchViewModel(SchedulerProvider, getSearchRepository(context)) as T
        }

    @Suppress("UNCHECKED_CAST")
    fun provideDetailViewModelFactory(
        context: Context,
        repoId: String,
        userName: String
    ): ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                DetailViewModel(
                    schedulerProvider = SchedulerProvider,
                    repositoryId = repoId,
                    userName = userName,
                    searchRepository = getSearchRepository(context),
                    userRepository = getUserRepository()
                ) as T
        }
}*/
