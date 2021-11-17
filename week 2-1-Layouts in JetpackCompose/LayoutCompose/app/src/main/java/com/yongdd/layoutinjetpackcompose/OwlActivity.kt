package com.yongdd.layoutinjetpackcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yongdd.layoutinjetpackcompose.ui.theme.LayoutInJetpackComposeTheme

class OwlActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutInJetpackComposeTheme {

                LayoutCodelab2()
            }
        }
    }
}

@Composable
fun LayoutCodelab2(){
    Scaffold (
        topBar = {
            TopAppBar (
                title = {Text(text="LayoutsCodelab")},
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ){innerPadding->
        BodyContent2(Modifier.padding(innerPadding))
    }
}

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows:Int = 3,
    content:@Composable () -> Unit
){
   Layout(
       modifier = modifier,
       content = content
   ) {  measurables, constraints ->
       val rowWidths = IntArray(rows){ 0 }
       val rowHeights = IntArray(rows) { 0 }
       val placeables = measurables.mapIndexed{index, measurable ->
           val placeable = measurable.measure(constraints)
           val row = index % rows
           rowWidths[row] += placeable.width
           rowHeights[row] = Math.max(rowHeights[row],placeable.height)

           placeable
       }


       val width = rowWidths.maxOrNull()
           ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth


       val height = rowHeights.sumOf { it }
           .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

       val rowY = IntArray(rows) { 0 }
       for (i in 1 until rows) {
           rowY[i] = rowY[i-1] + rowHeights[i-1]
       }

       layout(width, height) {
           val rowX = IntArray(rows) { 0 }

           placeables.forEachIndexed { index, placeable ->
               val row = index % rows
               placeable.placeRelative(
                   x = rowX[row],
                   y = rowY[row]
               )
               rowX[row] += placeable.width
           }
       }

   }
}

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChipPreview() {
    LayoutInJetpackComposeTheme{
        Chip(text = "Hi there")
    }
}

@Composable
fun BodyContent2(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .background(color=Color.LightGray)
        .padding(16.dp)
        .size(200.dp)
        .horizontalScroll(rememberScrollState()),content={
        StaggeredGrid {
            for (topic in topics) {
                Chip(modifier = Modifier.padding(8.dp), text = topic)
            }
        }
    })

}

@Preview
@Composable
fun LayoutsCodelabPreview() {
    LayoutInJetpackComposeTheme  {
        LayoutCodelab2()
    }
}





