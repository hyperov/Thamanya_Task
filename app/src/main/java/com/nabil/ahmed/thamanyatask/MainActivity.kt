package com.nabil.ahmed.thamanyatask

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabil.ahmed.thamanyatask.home.view.HomeScreen
import com.nabil.ahmed.thamanyatask.search.view.SearchScreen
import com.nabil.ahmed.thamanyatask.ui.theme.ThamanyaTaskTheme

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThamanyaTaskTheme {
                var searchQuery by remember { mutableStateOf("") }
                var searchActive by remember { mutableStateOf(false) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (searchActive) {
                            SearchBar(
                                query = searchQuery,
                                onQueryChange = { searchQuery = it },
                                onSearch = { searchActive = false },
                                active = searchActive,
                                onActiveChange = { searchActive = it },
                                placeholder = { Text("بحث") },
                                leadingIcon = {
                                    IconButton(onClick = { searchActive = false }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            ) {
                                SearchScreen(searchQuery = searchQuery)
                            }
                        } else {
                            TopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.primary
                                ),
                                title = { Text("ثمانية") },
                                actions = {
                                    IconButton(onClick = { searchActive = true }) {
                                        Icon(
                                            Icons.Filled.Search,
                                            contentDescription = "Search"
                                        )
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ThamanyaTaskTheme {
       HomeScreen(modifier = Modifier.padding(16.dp))
    }
}