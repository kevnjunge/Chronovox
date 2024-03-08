package com.example.chronovox.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.chronovox.theme.mediumBlue
import com.example.chronovox.theme.paperBackGround
import com.example.chronovox.theme.paperLineBlue
import com.example.chronovox.theme.verticalLineRed


@Composable
fun PaperText() {
    Box(
        modifier = Modifier
            .widthIn(400.dp, 800.dp)
            .height(540.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(paperBackGround)
        ) {
            // Draw the black circles
            Canvas(
                modifier = Modifier.matchParentSize()
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
                val paddingLeft = 40.dp.toPx() // Padding for the text area

                repeat(20) { index ->
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
                TextArea()
            }
        }
    }
}

@Composable
fun TextArea() {
    var text by remember { mutableStateOf("Hello world ! \n Here's a story for you...") }

    val sentences = text.split(". ") // Split text into sentences

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp) // Adjust padding to align with the lines
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            sentences.forEachIndexed { index, sentence ->
                BasicTextField(
                    value = sentence,
                    onValueChange = { text = it },
                    textStyle = TextStyle(
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        lineHeight = 30.sp,
                        color = mediumBlue
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                )
            }
        }
    }
}