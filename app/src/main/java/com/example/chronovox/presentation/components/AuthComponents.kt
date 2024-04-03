package com.example.chronovox.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chronovox.theme.ColombiaBlue
import com.example.chronovox.theme.DelftBlue

@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}


@Composable
fun InputTextField(
    labelValue: String,
    painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false,
    errorMessage: String? = null
) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = ColombiaBlue,
            focusedLabelColor = DelftBlue,
            cursorColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = { Icon(painter = painterResource, contentDescription = "") },
        isError = errorStatus
    )

    if (errorStatus && errorMessage != null) {
        Text(
            text = errorMessage,
            style = TextStyle(
                color = Color.Red,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(start = 16.dp),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun PasswordTextField(
    labelValue: String,
    painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false,
    errorMessage: String? = null
) {
    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = ColombiaBlue,
            focusedLabelColor = DelftBlue,
            cursorColor = DelftBlue
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = { Icon(painter = painterResource, contentDescription = "") },
        isError = errorStatus,
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )

    if (errorStatus && errorMessage != null) {
        Text(
            text = errorMessage,
            style = TextStyle(
                color = Color.Red,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(start = 16.dp),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun ClickableTextComponent(
    initialText: String,
    actionText: String,
    onTextSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {

        val annotatedString = buildAnnotatedString {
            append(initialText)
            withStyle(style = SpanStyle(color = DelftBlue)) {
                pushStringAnnotation(tag = actionText, annotation = actionText)
                append(actionText)
            }

        }

        ClickableText(text = annotatedString, onClick = { offset ->

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{${span.item}}")

                    if ((span.item == actionText)) {
                        onTextSelected(span.item)
                    }
                }

        })
    }

}

@Composable
fun RegularButtonComponent(value: String, onButtonClicked: () -> Unit, isEnabled: Boolean = false, errorMessages: List<String> = emptyList()) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .padding(vertical = 8.dp),
        onClick = {
            if (errorMessages.isEmpty()) {
                onButtonClicked()
            }
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(10.dp),
        enabled = isEnabled

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContinueWithGoogle(text: String, onclick: () -> Unit) {
    Card(
        onClick = onclick,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = com.google.android.gms.base.R.drawable.googleg_standard_color_18),
                contentDescription = "Google Icon",
                modifier = Modifier
                    .width(26.dp)
                    .height(26.dp)
            )
            Text(
                text = text,
                modifier = Modifier.padding(start = 12.dp),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
fun DividerTextComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Gray,
            thickness = 2.dp
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text = "or",
            fontSize = 18.sp,
            color = Color.Black
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Gray,
            thickness = 2.dp
        )
    }
}