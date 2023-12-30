package com.example.imagesapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersCardItem(
    val id: Int,
    val name: String,
    val thumbnail: String
): Parcelable
