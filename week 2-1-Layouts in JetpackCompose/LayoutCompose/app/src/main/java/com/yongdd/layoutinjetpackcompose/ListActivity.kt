package com.yongdd.layoutinjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.yongdd.layoutinjetpackcompose.ui.theme.LayoutInJetpackComposeTheme
import kotlinx.coroutines.launch

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutInJetpackComposeTheme {
                ImageList()
            }
        }
    }
}

@Composable
fun SimpleList(){
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)){
        repeat(100){
            Text(text = "Item #$it")
        }
    }
}

@Composable
fun LazyList(){
    val scrollState = rememberLazyListState()
    LazyColumn(state=scrollState){
        items(100){
            Text(text = "Item #$it")
        }
    }
}

@Composable
fun ImageListItem(index:Int){
    Row(verticalAlignment = Alignment.CenterVertically){

        Image(
            painter = rememberImagePainter(
                 data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text("Item #$index",style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun ImageList(){
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column {

        Row{
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }){Text("Scroll to the top")}
            Button(onClick = { coroutineScope.launch {  scrollState.animateScrollToItem(listSize-1)} }) {
                Text("Scroll to the end")
            }
        }
        LazyColumn(state=scrollState){
            items(100){
                ImageListItem(it)
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun showList(){
    LayoutInJetpackComposeTheme() {
        ImageList()
    }

}


