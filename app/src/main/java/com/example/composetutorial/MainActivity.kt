package com.example.composetutorial

import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.compose.foundation.layout.fillMaxSize

import android.os.Bundle //ok
import androidx.activity.ComponentActivity//ok
import androidx.activity.compose.setContent//ok
import androidx.compose.material.Text//ok
import androidx.compose.runtime.Composable//ok
import androidx.compose.ui.tooling.preview.Preview//ok
import androidx.compose.foundation.layout.Column//ok

import androidx.compose.foundation.Image//ok
import androidx.compose.foundation.layout.Row//ok
import androidx.compose.ui.res.painterResource//ok

import androidx.compose.foundation.layout.Spacer//ok
import androidx.compose.foundation.layout.height//ok
import androidx.compose.foundation.layout.padding//ok
import androidx.compose.foundation.layout.size//ok
import androidx.compose.foundation.layout.width//ok
import androidx.compose.foundation.shape.CircleShape//ok
import androidx.compose.ui.Modifier//ok
import androidx.compose.ui.draw.clip//ok
import androidx.compose.ui.unit.dp//ok

import androidx.compose.material.Surface//ok

import androidx.compose.foundation.border//ok
import androidx.compose.material.MaterialTheme//ok

import android.content.res.Configuration//ok
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.compose.tutorial.SampleData

import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)

@Preview
@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme {
        Surface {
            MessageCard(
                msg = Message("Colleague", "Take a look at Jetpack Compose, it's great!")
            )
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}