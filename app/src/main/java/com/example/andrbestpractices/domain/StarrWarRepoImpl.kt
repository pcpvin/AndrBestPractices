package com.example.andrbestpractices.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.andrbestpractices.model.StarWarsPeopleData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StarrWarRepoImpl @Inject constructor(private val service : StartWarApiService) : StarWarRepo {

    override suspend fun getStarWarsCharacter(): Flow<PagingData<StarWarsPeopleData>> =
        Pager(config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = { StarWarDataSource(service) }).flow
}
