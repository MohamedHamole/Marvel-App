package com.example.imagesapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.imagesapp.data.model.CharactersCardItem
import com.example.imagesapp.ui.theme.ImagesAppTheme

@Composable
fun CharactersCardList(
    charactersList: List<CharactersCardItem>,
    onItemClick: (data: CharactersCardItem) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = charactersList,
            key = {
                it.id
            })
        { items ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .clickable { onItemClick.invoke(CharactersCardItem(
                        id = items.id,
                        name = items.name,
                        thumbnail = items.thumbnail
                    )) }
            )
            {
                Box(modifier = Modifier.height(350.dp)) {
                    val painter = rememberAsyncImagePainter(
                        ImageRequest.
                        Builder(LocalContext.current)
                            .data(data = items.thumbnail)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(1000)
                            }).build()
                    )
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                        painter = painter,
                        contentDescription = ""
                    )
                    if (painter.state is AsyncImagePainter.State.Loading){
                        CircularProgressIndicator(modifier = Modifier
                            .align(alignment = Alignment.Center)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .align(alignment = Alignment.BottomCenter)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black),
                                    startY = 200f
                                )
                            )
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.BottomStart)
                            .padding(16.dp),
                        text = items.name,
                        style = TextStyle(color = Color.White, fontSize = 16.sp))
                }
            }
        }
    }
}

@Preview(device = Devices.PIXEL_4_XL)
@Composable
fun CharactersListPreview() {
    ImagesAppTheme {
        CharactersCardList(
            listOf(
                CharactersCardItem(
                    id = 1017100,
                    name = "A-Bomb (HAS)",
                    thumbnail = ""
                )
            ),
            onItemClick = {}
        )
    }
}