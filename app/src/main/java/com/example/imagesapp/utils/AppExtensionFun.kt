package com.example.imagesapp.utils

fun String.extractOutputKey(): String? {
    val regex = Regex("output/([a-zA-Z0-9]+)")
    val matchResult = regex.find(this)
    return matchResult?.groupValues?.get(1)
}