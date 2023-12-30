package com.example.imagesapp.data.model

data class UploadImageModel(
    val input: Input,
    val output: Output
)

data class Input(
    val size: Int,
    val type: String
)

data class Output(
    val height: Int,
    val ratio: Double,
    val size: Int,
    val type: String,
    val url: String,
    val width: Int
)