package com.example.imagesapp.data.model

data class ResizeImageRequestModel(
    val resize: Resize
){
    data class Resize(
        val method: String,
        val width: Int,
        val height: Int
    )
}

