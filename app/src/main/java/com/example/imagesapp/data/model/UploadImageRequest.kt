package com.example.imagesapp.data.model

data class UploadImageRequest(val source: Source){
    data class Source(val url: String)
}
