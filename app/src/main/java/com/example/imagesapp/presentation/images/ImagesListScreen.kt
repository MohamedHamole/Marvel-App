package com.example.imagesapp.presentation.images

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.imagesapp.data.model.CharactersCardItem
import com.example.imagesapp.presentation.component.CharactersCardList
import com.example.imagesapp.ui.theme.ImagesAppTheme
import com.example.imagesapp.utils.UiState
import okhttp3.internal.toImmutableList
import org.koin.androidx.compose.getViewModel
import com.example.imagesapp.utils.toImagesEntityList
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.imagesapp.navigation.ScreensRoute
import com.example.imagesapp.utils.ConnectivityObserver
import com.example.imagesapp.utils.NetworkConnectivityObserver
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import java.net.URLEncoder

@Composable
fun ImagesListScreen(navController: NavHostController, viewModel: ImagesViewModel = getViewModel()) {

    val context = LocalContext.current
    val connectivityObserver = remember { NetworkConnectivityObserver(context) }
    var isInternet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        connectivityObserver.observe().onEach {
            isInternet = when(it){
                ConnectivityObserver.Status.Available -> { true }
                else -> { false }
            }
            viewModel.getCharacters(isInternet)
        }.collect()

    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        // ... handle selected image URI ...
    }

    when (val data = viewModel.charactersSF.collectAsState().value) {
        is UiState.Loading -> {}
        is UiState.Success -> {
            ImagesListScreenContent(navController, result = data.data, imagePickerLauncher = imagePickerLauncher)
            if (isInternet){
                LaunchedEffect(Unit) {
                    viewModel.insertImagesToDatabaseFromRemote(data.data.toImagesEntityList())
                }
            }

        }
        is UiState.Error -> {}

        else -> {}
    }
}

@Composable
fun ImagesListScreenContent(
    navController: NavHostController,
    result: List<CharactersCardItem>,
    imagePickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
) {

    Box(modifier = Modifier.fillMaxSize()) {
        CharactersCardList(charactersList = result.toImmutableList(),
            onItemClick = {
                val imageDetails = CharactersCardItem(it.id, it.name, it.thumbnail)
                val encodedImageUrl = URLEncoder.encode(imageDetails.thumbnail, "UTF-8")
                val encodedImageCaption = URLEncoder.encode(imageDetails.name, "UTF-8")
                navController.navigate("${ScreensRoute.Details.route}/${encodedImageUrl}/${encodedImageCaption}/${imageDetails.id}")
            })

        FloatingActionButton(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(end = 16.dp, bottom = 16.dp),
            onClick = {
                imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = Color.Unspecified,
                contentDescription = "Upload Image"
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun CharactersScreenPreview() {
    ImagesAppTheme {
        ImagesListScreenContent(
            navController = rememberNavController(),
            listOf(
            CharactersCardItem(
                id = 1017100, name = "A-Bomb (HAS)", thumbnail = ""
            )
        ), imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri: Uri? ->

        })
    }
}