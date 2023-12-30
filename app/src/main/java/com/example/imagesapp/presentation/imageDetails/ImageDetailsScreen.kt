package com.example.imagesapp.presentation.imageDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.imagesapp.data.model.CharactersCardItem
import com.example.imagesapp.presentation.component.AppTextField
import com.example.imagesapp.ui.theme.ImagesAppTheme
import com.example.imagesapp.utils.UiState
import org.koin.androidx.compose.getViewModel

@Composable
fun ImageDetailsScreen(
    imageUrl: String,
    imageCaption: String,
    id: Int,
    navController: NavHostController,
    viewModel: ImageDetailsViewModel = getViewModel()
) {

    var caption by remember { mutableStateOf(imageCaption) }
    var widthInputValue by remember { mutableStateOf("") }
    var heightInputValue by remember { mutableStateOf("") }
    var resizedImage = viewModel.imageResized.collectAsState(initial = imageUrl).value

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(1000)
            }).build()
    )

    ImageDetailsScreenContent(painter = painter,
        imageUrl = imageUrl,
        caption = caption,
        id = id,
        widthInputValue = widthInputValue,
        heightInputValue = heightInputValue,
        onCaptionChange = { newCaption -> caption = newCaption },
        onWidthInputChange = { newWidth -> widthInputValue = newWidth },
        onHeightInputChange = { newHeight -> heightInputValue = newHeight },
        onSubmitClicked = {
            viewModel.resizeImage(imageUrl, widthInputValue.toInt(), heightInputValue.toInt())
        })

}

@Composable
fun ImageDetailsScreenContent(
    painter: AsyncImagePainter,
    imageUrl: String,
    caption: String,
    id: Int,
    widthInputValue: String,
    heightInputValue: String,
    onCaptionChange: (String) -> Unit,
    onWidthInputChange: (String) -> Unit,
    onHeightInputChange: (String) -> Unit,
    onSubmitClicked: (data: CharactersCardItem) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Box(modifier = Modifier.height(350.dp)) {

                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    painter = painter,
                    contentDescription = ""
                )
                if (painter.state is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(alignment = Alignment.Center)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .align(alignment = Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black), startY = 200f
                            )
                        )
                )

                BasicTextField(
                    value = caption,
                    onValueChange = { onCaptionChange(it) },
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.BottomStart)
                        .padding(16.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AppTextField(hint = "width",
                getInputValue = { widthInputValue },
                onValueChange = { onWidthInputChange(it) },
                modifier = Modifier.weight(1f)
            )

            AppTextField(hint = "height",
                getInputValue = { heightInputValue },
                onValueChange = { onHeightInputChange(it) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    onSubmitClicked.invoke(
                        CharactersCardItem(
                            id = id, thumbnail = imageUrl, name = caption
                        )
                    )
                },
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp)
                    .background(color = Color.Transparent)
            ) {
                Text(
                    text = "Submit", style = TextStyle().copy(
                        fontSize = 22.sp, color = Color.White
                    ), modifier = Modifier.padding(
                        start = 50.dp, end = 50.dp, top = 8.dp, bottom = 8.dp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun ImageDetailsPreview() {
    ImagesAppTheme {
        ImageDetailsScreenContent(painter = rememberAsyncImagePainter(model = ""),
            imageUrl = "",
            caption = "",
            id = 0,
            widthInputValue = "",
            heightInputValue = "",
            onCaptionChange = {},
            onWidthInputChange = {},
            onHeightInputChange = {},
            onSubmitClicked = {})
    }
}