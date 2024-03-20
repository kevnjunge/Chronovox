package com.example.chronovox.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.chronovox.presentation.screens.detail.DetailUiState
import com.example.chronovox.presentation.screens.detail.DetailViewModel
import com.example.chronovox.theme.BgWhite
import com.example.chronovox.theme.MicadoYellow
import com.example.chronovox.theme.mediumBlue
import com.example.chronovox.theme.paperBackGround
import com.example.chronovox.theme.paperLineBlue
import com.example.chronovox.theme.verticalLineRed


@Composable
fun PaperText(
    detailViewModel: DetailViewModel?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .imePadding()
                .clip(RoundedCornerShape(10.dp))
                .background(paperBackGround)
        ) {
            // Draw the black circles
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .padding(vertical = 20.dp)
            ) {
                val circleRadius = 5.dp.toPx()
                val circleSpacing = 30.dp // Adjust for desired spacing between circles
                val numberOfCircles = (size.height / circleSpacing.toPx()).toInt()

                val paddingLeft = 8.dp.toPx() // Padding between circles and left side


                repeat(numberOfCircles) { index ->
                    val centerY = (index * circleSpacing).toPx()
                    drawCircle(
                        color = Color(0xFF575450),
                        radius = circleRadius,
                        center = Offset(paddingLeft + circleRadius, centerY)
                    )
                }
            }

            // Draw the vertical line
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                val lineColor = verticalLineRed
                val lineThickness = 3.dp.toPx()
                val paddingLeft = 40.dp.toPx() // Padding between red line and circles

                drawLine(
                    color = lineColor,
                    start = Offset(paddingLeft, 0f),
                    end = Offset(paddingLeft, size.height),
                    strokeWidth = lineThickness
                )
            }

            // Draw the horizontal lines
            Canvas(
                modifier = Modifier.matchParentSize()

            ) {
                val lineColor = paperLineBlue
                val lineThickness = 1.dp.toPx()
                val lineSpacing = 30.dp // Adjust for desired spacing between lines
                val paddingLeft = 40.dp.toPx() // Padding for the text
                val paddingTop = 20.dp.toPx() // Padding for the top of the text area
                val paddingBottom = 20.dp.toPx() // Padding for the bottom of the text area

                // Calculate the number of lines based on the available height
                val availableHeight = size.height - paddingTop - paddingBottom
                val numberOfLines = (availableHeight / lineSpacing.toPx()).toInt()

                repeat(numberOfLines) { index ->
                    val yPosition = (index * lineSpacing).toPx()
                    drawLine(
                        color = lineColor,
                        start = Offset(paddingLeft, yPosition), // Padding for the text area
                        end = Offset(size.width, yPosition),
                        strokeWidth = lineThickness
                    )
                }
            }

            // Text area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 36.dp, top = 0.dp, end = 0.dp, bottom = 0.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                TextArea(detailViewModel= detailViewModel)
            }
        }
    }
}

@Composable
fun TextArea(
    detailViewModel: DetailViewModel?
) {
    val detailUiState = detailViewModel?.detailUiState ?: DetailUiState()
    var text by remember { mutableStateOf("Hello world ! \n Here's a story for you...") }

    val sentences = text.split(". ") // Split text into sentences

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp), // Adjust padding to align with the lines
        verticalArrangement = Arrangement.SpaceBetween // Place items at the start and end of the column
    ) {
        // Text area occupies maximum available space
        Box(
            modifier = Modifier
                .weight(1f) // Text area takes up most of the available space
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                sentences.forEachIndexed { index, sentence ->
                    BasicTextField(
                        value = detailUiState.journalEntry,
                        onValueChange = {detailViewModel?.onJournalEntryChange(it) },
                        textStyle = TextStyle(
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            lineHeight = 30.sp,
                            color = mediumBlue,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                    )
                }
            }
        }
        // Add the MediaIcons composable here at the bottom
        MediaIcons()
    }
}



@Composable
fun MediaIcons(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .background(color = BgWhite, shape = RoundedCornerShape(50.dp)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedIconButton(
                onClick = { /*TODO*/ },
                icon = Icons.Outlined.Mic,
                contentDescription = "Mic Button",
                tint = MicadoYellow
            )
            OutlinedIconButton(
                onClick = { /*TODO*/ },
                icon = Icons.Outlined.Photo,
                contentDescription = "Photo Button",
                tint = MicadoYellow
            )
            OutlinedIconButton(
                onClick = { /*TODO*/ },
                icon = Icons.Outlined.Videocam,
                contentDescription = "Video Button",
                tint = MicadoYellow
            )
        }

        Row(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                onClick = {/*TODO*/ },
                shape = CircleShape,
                containerColor = BgWhite
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = MicadoYellow
                )
            }
        }
    }

}


@Composable
fun OutlinedIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    tint: Color
) {
    IconButton(
        onClick = onClick,

        ) {
        Icon(
            icon,
            contentDescription,

            tint = tint
        )
    }
}
