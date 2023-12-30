package com.example.imagesapp.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imagesapp.ui.theme.ImagesAppTheme
import com.example.imagesapp.ui.theme.strokeColor

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(hint: String,
                 getInputValue: () -> String,
                 onValueChange: (String) -> Unit,
                 modifier: Modifier = Modifier
                 ) {

    val keyboardController = LocalSoftwareKeyboardController.current

        TextField(
            value = getInputValue(),
            onValueChange = { newValue -> onValueChange(newValue) },
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.Black),
            modifier = modifier
                .padding(16.dp)
                .background(color = Color.Transparent)
                .border(
                    BorderStroke(1.dp, strokeColor),
                    shape = RoundedCornerShape(16.dp)
                ),
            placeholder = { Text(hint, color = Color.Gray) },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
}

@Preview
@Composable
fun AppTextFieldPreview() {
    ImagesAppTheme {
        AppTextField(
            hint = "",
            getInputValue = {""},
            onValueChange = {})
    }
}