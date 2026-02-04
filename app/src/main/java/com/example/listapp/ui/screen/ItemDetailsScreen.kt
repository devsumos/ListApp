package com.example.listapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun ItemDetailsScreen(
    itemId: Int?,
    itemTitle: String,
    itemBody: String,
    isLoading: Boolean,
    onTitleChange: (String) -> Unit,
    onBodyChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            if (isLoading) {
                item("loading") {
                    CircularProgressIndicator()
                }
            } else if (itemId != null) {
                item("image_and_title") {
                    val modelUrl = remember(itemId) {
                        "https://picsum.photos/300/300?random=${itemId}&grayscale"
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AsyncImage(
                            model = modelUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(80.dp)
                        )
                        OutlinedTextField(
                            label = { Text("Title") },
                            value = itemTitle,
                            onValueChange = onTitleChange,
                        )
                    }
                }
                item("body") {
                    OutlinedTextField(
                        label = { Text("Body") },
                        value = itemBody,
                        onValueChange = onBodyChange,
                    )
                }
                item("save_button") {
                    Button(
                        onClick = onSave,
                        enabled = !itemTitle.isBlank() && !itemBody.isBlank()
                    ) {
                        Text("Save Changes")
                    }
                }
            }
        }
    }
}