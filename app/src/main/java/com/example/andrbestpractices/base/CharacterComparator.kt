package com.example.andrbestpractices.base

import androidx.recyclerview.widget.DiffUtil
import com.example.andrbestpractices.model.StarWarsPeopleData

object CharacterComparator : DiffUtil.ItemCallback<StarWarsPeopleData>() {

        override fun areItemsTheSame(oldItem: StarWarsPeopleData, newItem: StarWarsPeopleData) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: StarWarsPeopleData, newItem: StarWarsPeopleData) =
            oldItem == newItem
    }