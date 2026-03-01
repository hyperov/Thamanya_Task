package com.nabil.ahmed.thamanyatask.home.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.nabil.ahmed.thamanyatask.home.model.res.Content
import com.nabil.ahmed.thamanyatask.home.model.res.Section
import com.nabil.ahmed.thamanyatask.ui.components.SectionsScreen
import com.nabil.ahmed.thamanyatask.ui.theme.ThamanyaTaskTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for the HomeScreen rendering behavior.
 *
 * Tests target [SectionsScreen] directly — the composable [HomeScreen] delegates all rendering to —
 * using static [PagingData] to isolate the UI layer from Hilt / ViewModel / network dependencies.
 */
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // region helpers

    private fun content(
        name: String = "Test Content",
        description: String = "Test description",
        avatarUrl: String = "",
        duration: Int = 600,
        authorName: String? = "Test Author",
        episodeCount: Int? = 10,
        podcastName: String? = null,
        podcastPriority: Int? = null,
        priority: Int? = null,
    ) = Content(
        avatarUrl = avatarUrl,
        description = description,
        duration = duration,
        name = name,
        score = 4.0,
        authorName = authorName,
        episodeCount = episodeCount,
        podcastName = podcastName,
        podcastPriority = podcastPriority,
        priority = priority,
    )

    private fun section(
        name: String,
        type: String,
        order: Int = 1,
        contentType: String = "podcast",
        content: List<Content> = listOf(content()),
    ) = Section(
        content = content,
        contentType = contentType,
        name = name,
        order = order,
        type = type,
    )

    private fun setScreenContent(sections: List<Section>) {
        val flow = MutableStateFlow(PagingData.from(sections))

        composeTestRule.setContent {
            ThamanyaTaskTheme(dynamicColor = false) {
                val lazyPagingItems = flow.collectAsLazyPagingItems()
                SectionsScreen(
                    lazySections = lazyPagingItems,
                    modifier = Modifier.fillMaxSize(),
                    isSearchScreen = false,
                )
            }
        }
    }

    // endregion

    // region section headers

    @Test
    fun sectionHeader_isDisplayed() {
        setScreenContent(listOf(section(name = "Trending Podcasts", type = "square")))

        composeTestRule.onNodeWithText("Trending Podcasts").assertIsDisplayed()
    }

    @Test
    fun multipleSections_allHeadersAreDisplayed() {
        setScreenContent(
            listOf(
                section(name = "First Section", type = "square", order = 1),
                section(name = "Second Section", type = "square", order = 2),
            )
        )

        composeTestRule.onNodeWithText("First Section").assertIsDisplayed()
        composeTestRule.onNodeWithText("Second Section").assertIsDisplayed()
    }

    // endregion

    // region square section type

    @Test
    fun squareSection_displaysContentName() {
        val item = content(name = "My Podcast")
        setScreenContent(listOf(section(name = "Podcasts", type = "square", content = listOf(item))))

        composeTestRule.onNodeWithText("My Podcast").assertIsDisplayed()
    }

    @Test
    fun squareSection_displaysEpisodesBadge() {
        val item = content(name = "Podcast", episodeCount = 42)
        setScreenContent(listOf(section(name = "Section", type = "square", content = listOf(item))))

        composeTestRule.onNodeWithText("Episodes 42").assertIsDisplayed()
    }

    @Test
    fun squareSection_displaysAuthorName_whenPresent() {
        val item = content(name = "Podcast", authorName = "John Doe")
        setScreenContent(listOf(section(name = "Section", type = "square", content = listOf(item))))

        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
    }

    @Test
    fun squareSection_rendersWithoutCrash_whenAuthorIsNull() {
        val item = content(name = "Podcast Name", authorName = null, episodeCount = 10)
        setScreenContent(listOf(section(name = "Section", type = "square", content = listOf(item))))

        composeTestRule.onNodeWithText("Podcast Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Episodes 10").assertIsDisplayed()
    }

    @Test
    fun squareSection_displaysMultipleItems() {
        val items = listOf(
            content(name = "Podcast A", priority = 1),
            content(name = "Podcast B", priority = 2),
        )
        setScreenContent(listOf(section(name = "Section", type = "square", content = items)))

        composeTestRule.onNodeWithText("Podcast A").assertIsDisplayed()
        composeTestRule.onNodeWithText("Podcast B").assertIsDisplayed()
    }

    @Test
    fun squareSection_sortsContentByPriority() {
        val items = listOf(
            content(name = "Low Priority", priority = 5),
            content(name = "High Priority", priority = 1),
        )
        setScreenContent(listOf(section(name = "Section", type = "square", content = items)))

        composeTestRule.onNodeWithText("High Priority").assertIsDisplayed()
        composeTestRule.onNodeWithText("Low Priority").assertIsDisplayed()
    }

    // endregion

    // region big_square section type

    @Test
    fun bigSquareSection_displaysContentName() {
        val item = content(name = "Featured Article", authorName = "Author X")
        setScreenContent(listOf(section(name = "Featured", type = "big_square", content = listOf(item))))

        composeTestRule.onNodeWithText("Featured").assertIsDisplayed()
        composeTestRule.onNodeWithText("Featured Article").assertIsDisplayed()
    }

    @Test
    fun bigSquareSection_displaysAuthorAndDescription() {
        val item = content(
            name = "Article",
            authorName = "Jane Smith",
            description = "An interesting article about tech",
        )
        setScreenContent(listOf(section(name = "Section", type = "big_square", content = listOf(item))))

        composeTestRule.onNodeWithText("Jane Smith").assertIsDisplayed()
        composeTestRule.onNodeWithText("An interesting article about tech").assertIsDisplayed()
    }

    @Test
    fun bigSquareSection_displaysDurationBadge_hoursAndMinutes() {
        val item = content(name = "Long Article", duration = 3661)
        setScreenContent(listOf(section(name = "Section", type = "big_square", content = listOf(item))))

        composeTestRule.onNodeWithText("1h 1m").assertIsDisplayed()
    }

    @Test
    fun bigSquareSection_displaysDurationBadge_minutesOnly() {
        val item = content(name = "Short Article", duration = 600)
        setScreenContent(listOf(section(name = "Section", type = "big_square", content = listOf(item))))

        composeTestRule.onNodeWithText("10m").assertIsDisplayed()
    }

    @Test
    fun bigSquareSection_rendersWithoutCrash_whenDescriptionIsBlank() {
        val item = content(name = "Article", authorName = "Author", description = "")
        setScreenContent(listOf(section(name = "Section", type = "big_square", content = listOf(item))))

        composeTestRule.onNodeWithText("Article").assertIsDisplayed()
        composeTestRule.onNodeWithText("Author").assertIsDisplayed()
    }

    @Test
    fun bigSquareSection_rendersWithoutCrash_whenAuthorIsNull() {
        val item = content(name = "Solo Article", authorName = null)
        setScreenContent(listOf(section(name = "Section", type = "big_square", content = listOf(item))))

        composeTestRule.onNodeWithText("Solo Article").assertIsDisplayed()
    }

    // endregion

    // region queue section type

    @Test
    fun queueSection_displaysContentNameAndAuthor() {
        val item = content(name = "Queue Item", authorName = "Producer")
        setScreenContent(listOf(section(name = "Listen Queue", type = "queue", content = listOf(item))))

        composeTestRule.onNodeWithText("Listen Queue").assertIsDisplayed()
        composeTestRule.onNodeWithText("Queue Item").assertIsDisplayed()
        composeTestRule.onNodeWithText("Producer").assertIsDisplayed()
    }

    @Test
    fun queueSection_displaysDescription() {
        val item = content(name = "Item", description = "Fascinating deep dive")
        setScreenContent(listOf(section(name = "Queue", type = "queue", content = listOf(item))))

        composeTestRule.onNodeWithText("Fascinating deep dive").assertIsDisplayed()
    }

    @Test
    fun queueSection_displaysDuration() {
        val item = content(name = "Item", duration = 7200)
        setScreenContent(listOf(section(name = "Queue", type = "queue", content = listOf(item))))

        composeTestRule.onNodeWithText("2h 0m").assertIsDisplayed()
    }

    @Test
    fun queueSection_displaysEpisodesBadge() {
        val item = content(name = "Item", episodeCount = 25)
        setScreenContent(listOf(section(name = "Queue", type = "queue", content = listOf(item))))

        composeTestRule.onNodeWithText("Episodes 25").assertIsDisplayed()
    }

    @Test
    fun queueSection_rendersWithoutCrash_whenAuthorIsNull() {
        val item = content(name = "Solo Queue Item", authorName = null, description = "Still visible")
        setScreenContent(listOf(section(name = "Queue", type = "queue", content = listOf(item))))

        composeTestRule.onNodeWithText("Solo Queue Item").assertIsDisplayed()
        composeTestRule.onNodeWithText("Still visible").assertIsDisplayed()
    }

    // endregion

    // region 2_lines_grid section type

    @Test
    fun twoLinesGridSection_displaysTitleAndSubtitle() {
        val item = content(name = "Episode Title", podcastName = "Podcast Subtitle", duration = 300)
        setScreenContent(
            listOf(section(name = "Latest Episodes", type = "2_lines_grid", content = listOf(item)))
        )

        composeTestRule.onNodeWithText("Latest Episodes").assertIsDisplayed()
        composeTestRule.onNodeWithText("Episode Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Podcast Subtitle").assertIsDisplayed()
    }

    @Test
    fun twoLinesGridSection_displaysDurationBadge() {
        val item = content(name = "Episode", podcastName = "Podcast", duration = 125)
        setScreenContent(
            listOf(section(name = "Section", type = "2_lines_grid", content = listOf(item)))
        )

        composeTestRule.onNodeWithText("2m 5s").assertIsDisplayed()
    }

    @Test
    fun twoLinesGridSection_showsEmptySubtitle_whenPodcastNameIsNull() {
        val item = content(name = "Episode", podcastName = null, duration = 60)
        setScreenContent(
            listOf(section(name = "Section", type = "2_lines_grid", content = listOf(item)))
        )

        composeTestRule.onNodeWithText("Episode").assertIsDisplayed()
    }

    // endregion

    // region empty state

    @Test
    fun emptyPagingData_displaysNoSections() {
        setScreenContent(emptyList())

        composeTestRule.onAllNodesWithText("Section", substring = true).assertCountEquals(0)
    }

    // endregion

    // region mixed section types

    @Test
    fun mixedSectionTypes_allRenderCorrectly() {
        val squareItem = content(name = "Square Podcast", episodeCount = 5)
        val bigSquareItem = content(name = "Big Square Article", authorName = "Big Author", duration = 1800)
        val queueItem = content(name = "Queue Episode", authorName = "Queue Author", description = "Queue desc")

        setScreenContent(
            listOf(
                section(name = "Squares", type = "square", order = 1, content = listOf(squareItem)),
                section(name = "Big Squares Items", type = "big_square", order = 2, content = listOf(bigSquareItem)),
                section(name = "Queue Items", type = "queue", order = 3, content = listOf(queueItem)),
            )
        )

        composeTestRule.onNodeWithText("Squares").assertIsDisplayed()
        composeTestRule.onNodeWithText("Square Podcast").assertIsDisplayed()

        val lazyColumnMatcher = SemanticsMatcher.keyIsDefined(
            SemanticsProperties.VerticalScrollAxisRange
        )

        composeTestRule
            .onNode(lazyColumnMatcher)
            .performScrollToNode(hasText("Big Squares Items"))

        composeTestRule.onNodeWithText("Big Squares Items").assertIsDisplayed()
        composeTestRule.onNodeWithText("Big Square Article").assertIsDisplayed()

        composeTestRule
            .onNode(lazyColumnMatcher)
            .performScrollToNode(hasText("Queue Items"))

        composeTestRule.onNodeWithText("Queue Items").assertIsDisplayed()
        composeTestRule.onNodeWithText("Queue Episode").assertIsDisplayed()
    }

    // endregion
}
