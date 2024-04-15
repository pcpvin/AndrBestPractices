package com.example.andrbestpractices.domain

import androidx.paging.PagingData
import com.example.andrbestpractices.model.StarWarsPeopleData
import com.example.gojekpractice.model.StarWarsPeopleData
import kotlinx.coroutines.flow.Flow

interface StarWarRepo {
    suspend fun getStarWarsCharacter(): Flow<PagingData<StarWarsPeopleData>>
}