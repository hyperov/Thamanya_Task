package com.nabil.ahmed.thamanyatask.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.nabil.ahmed.thamanyatask.home.model.res.Content

@Composable
fun SquareComponent(
    article: Content,
    modifier: Modifier = Modifier,
    onClick: (Content) -> Unit = {}
) {
    Card(
        modifier = modifier.clickable { onClick(article) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (article.avatarUrl.isNotBlank()) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = article.avatarUrl,
                    contentDescription = article.name,
                    contentScale = ContentScale.Crop,
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.4f to Color.Transparent,
                                1.0f to Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
            )

            // Score Badge
            EpisodesBadge(
                episodeCount = article.episodeCount ?: 0,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Text(
                    text = article.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (!article.authorName.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = article.authorName,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun EpisodesBadge(
    episodeCount: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = Color.Black.copy(alpha = 0.6f)
    ) {
        Text(
            text = "Episodes $episodeCount",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    return "$minutes min read"
}


@Preview(showBackground = true)
@Composable
fun SquareComponentPreview() {
    val article = Content(
        articleId = "article_001",
        name = "The Future of AI",
        authorName = "Tech World",
        description = "An in-depth look at the future impact of artificial intelligence.",
        avatarUrl = "file:///android_res/drawable/ic_launcher_foreground",
        duration = 1200,
        releaseDate = "2023-05-10T10:00:00Z",
        score = 300.toDouble(),
        episodeCount = 200
    )

    SquareComponent(
        article = article,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}