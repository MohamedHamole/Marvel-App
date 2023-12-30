package com.example.imagesapp.utils

import com.example.imagesapp.data.local.ImagesEntity
import com.example.imagesapp.data.model.CharactersCardItem
import com.example.imagesapp.data.model.CharactersModel


fun CharactersModel.toCharactersCardItem(): List<CharactersCardItem> {
    return data.results.map {
        CharactersCardItem(
            id = it.id,
            name = it.name ,
            thumbnail = "${it.thumbnail.path}.${it.thumbnail.extension}"
        )
    }
}

fun List<CharactersCardItem>.toImagesEntityList(): List<ImagesEntity> {
    return map { charactersCardItem ->
        ImagesEntity(
            id = charactersCardItem.id,
            name = charactersCardItem.name,
            thumbnail = charactersCardItem.thumbnail
        )
    }
}

fun List<ImagesEntity>.toCharactersCardItemList(): List<CharactersCardItem> {
    return map { imagesEntity ->
        CharactersCardItem(
            id = imagesEntity.id,
            name = imagesEntity.name,
            thumbnail = imagesEntity.thumbnail
        )
    }
}
