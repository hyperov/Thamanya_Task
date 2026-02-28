package com.nabil.ahmed.thamanyatask

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nabil.ahmed.thamanyatask.home.model.res.Content
import com.nabil.ahmed.thamanyatask.home.view.HomeScreen
import com.nabil.ahmed.thamanyatask.ui.components.SquareComponent
import com.nabil.ahmed.thamanyatask.ui.components.SquareWithTitlesAndSubtitles
import com.nabil.ahmed.thamanyatask.ui.theme.ThamanyaTaskTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThamanyaTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Column {
//                        SquareComponent(
//                            article = article,
//                            modifier = Modifier
//                                .padding(16.dp)
//                                .fillMaxWidth()
//                        )
//
//                        SquareWithTitlesAndSubtitles(
//                            imageUrl = article.avatarUrl,
//                            title = article.name,
//                            subtitle = article.authorName ?: article.podcastName ?: "",
//                            modifier = Modifier.padding(8.dp),
//                            onClick = { /* ... */ },
//                            duration = article.duration
//                        )
//                    }
                    HomeScreen()
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                }
            }
        }
    }
}

val article = Content(
    articleId = "article_001",
    name = "The Future of AI",
    authorName = "Tech World",
    description = "An in-depth look at the future impact of artificial intelligence.",
    avatarUrl = "https://media.npr.org/assets/img/2018/08/03/npr_tbl_podcasttile_sq-284e5618e2b2df0f3158b076d5bc44751e78e1b6.jpg?s=1400&c=66&f=jpg",
    duration = 1200,
    releaseDate = "2023-05-10T10:00:00Z",
    score = 300.toDouble(),
    episodeCount = 200
)



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ThamanyaTaskTheme {
        Greeting("Android")
    }
}