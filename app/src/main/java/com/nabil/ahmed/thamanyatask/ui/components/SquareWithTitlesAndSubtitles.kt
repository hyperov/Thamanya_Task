package com.nabil.ahmed.thamanyatask.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

/**
 * A horizontal row: square image on the left, and beside it two lines of text (title and subtitle) stacked vertically.
 */
@Composable
fun SquareWithTitlesAndSubtitles(
    imageUrl: String,
    title: String,
    subtitle: String?,
    duration: Int,
    authorName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Surface(shape = RoundedCornerShape(10.dp)) {
        Row(
            modifier = modifier
                .width(300.dp)
                .background(color = MaterialTheme.colorScheme.secondary)
                .clip(RoundedCornerShape(10.dp))
                .padding(8.dp)
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                if (imageUrl.isNotBlank()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if(subtitle.isNullOrEmpty()) authorName else subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Duration2Badge(
                        duration = formatDurationCompact(duration),
                        modifier = Modifier
                    )

                }
            }
        }
    }
}

private fun formatDurationCompact(seconds: Int): String {
    val totalMinutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "${totalMinutes}m ${remainingSeconds}s"
}

@Composable
private fun Duration2Badge(
    duration: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = duration,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SquareWithTitlesRowPreview() {
    SquareWithTitlesAndSubtitles(
        imageUrl = "https://media.npr.org/assets/img/2023/10/11/stateofworld_sq-75fa8776ed49f02437f7283e25e054b9cc4db31c.jpg",
        title = "State of the World from NPR",
        subtitle = "A human perspective on global stories",
        modifier = Modifier.padding(16.dp),
        duration = 200,
        authorName = "SAN TSU"
    )
}