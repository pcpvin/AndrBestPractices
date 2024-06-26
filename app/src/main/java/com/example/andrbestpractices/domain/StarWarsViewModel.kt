package com.example.andrbestpractices.domain

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andrbestpractices.base.BaseViewModel
import com.example.andrbestpractices.model.StarWarsPeopleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StarWarsViewModel @Inject constructor(private val repo: StarWarRepo) : BaseViewModel() {

    init {
        getAllCharacters()
    }

    private lateinit var _starWarFlow: Flow<PagingData<StarWarsPeopleData>>
    val starWarFlow: Flow<PagingData<StarWarsPeopleData>>
        get() = _starWarFlow

    private fun getAllCharacters() = launchPagingAsync({
        repo.getStarWarsCharacter().cachedIn(viewModelScope)
    }, {
        _starWarFlow = it
    })
}