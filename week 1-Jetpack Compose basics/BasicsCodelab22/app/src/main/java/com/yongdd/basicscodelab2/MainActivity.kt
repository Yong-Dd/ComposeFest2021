package com.yongdd.basicscodelab2

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yongdd.basicscodelab2.ui.theme.BasicsCodelab2Theme
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelab2Theme {
//                // A surface container using the 'background' color from the theme
                    MyApp()
//                    var names = listOf<String>("android","pp","didi","nunu","nana")
//                    Greeting(names)

//                Conversation(SampleData.conversationSample)
            }



        }
    }
}



@Composable
fun OnboardingScreen(onContinueClicked : ()->Unit){

    Surface{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basic Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ){
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview(){
    BasicsCodelab2Theme {
        OnboardingScreen(onContinueClicked = {})
    }
}

//var names = listOf<String>("android","pp","didi","nunu","nana")
@Composable
private fun MyApp(){
    var shouldShowOnboarding by rememberSaveable{ mutableStateOf(true)}
    if(shouldShowOnboarding){
        OnboardingScreen(onContinueClicked  = {shouldShowOnboarding = false})
    }else{
        Greetings()
    }

}

@Composable
fun Greetings(names: List<String> = List(1000){"$it"}){
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)){
       items(items = names){name ->
           Greeting(name = name)
       }
    }
}

@Composable
fun Greeting(name: String){
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp,horizontal = 8.dp)
    ){
        CardContent(name)
    }


}

@Composable
private fun CardContent(name:String){
    var expanded by remember{ mutableStateOf(false)}
//    val extraPadding by animateDpAsState(if(expanded)48.dp else 0.dp,
//        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow)
//    )
    Surface(color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp,horizontal = 8.dp)){
        Row(modifier = Modifier.padding(12.dp)
            .animateContentSize(animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow)
            )){
            Column(modifier = Modifier
                .weight(1f)
                .padding(12.dp)) {
                Text("Hello!")
                Text(name,style = typography.h4.copy(fontWeight = FontWeight.ExtraBold))
                if(expanded){
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4)
                    )
                }
            }
            IconButton(onClick = {expanded = !expanded}){
                Icon(
                    imageVector = if(expanded) Filled.KeyboardArrowDown else Filled.KeyboardArrowUp,
                    contentDescription = if(expanded){
                        stringResource(R.string.show_less)
                    }else{
                        stringResource(id = R.string.show_more)
                    }
                )
            }

        }

    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelab2Theme {
        MyApp()

    }
}




data class Message(val author:String, val body :String)


@Composable
fun MessageCard(name: Message) {
//    Text(text = "Hello!")
    Row(modifier = Modifier.padding(all = 8.dp)){
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = "profile",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

//        val surfaceColor : Color by animateColorAsState(
//            if(isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
//        )
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text=name.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = shapes.medium,
            elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ){
                Text(text=name.body,
                    style = typography.body2)
            }
        }
    }
}





@Composable
fun Conversation(messages:List<Message>){
    LazyColumn{
        items(messages){
            message->
            MessageCard(message)
        }
    }
}

//@Preview(name = "Light Mode")
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true,
//    name = "Dark Mode"
//)
//@Composable
//fun PreviewConversation(){
//    BasicsCodelab2Theme {
//        Conversation(SampleData.conversationSample)
//    }
//}




