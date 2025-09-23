@file:OptIn(ExperimentalFoundationApi::class)

package com.stalzi.patientcards

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScaffold()
        }
    }
}

@Composable
fun MainScaffold() {
    val components = remember { mutableStateListOf<String>() }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { components.add("Component ${components.size}") }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.Gray),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            itemsIndexed(components) { index, component ->
                key(component) {
                    Row(
                        modifier = Modifier
                                .background(Color.Cyan)
                                .height(80.dp)
                                .fillMaxWidth()
                                .dragAndDropSource {
                                    detectTapGestures { offset ->
                                        startTransfer(
                                            transferData = DragAndDropTransferData(
                                                clipData = ClipData.newPlainText(
                                                    "text",
                                                    "$index",
                                                )
                                            )
                                        )
                                    }
                                }
                                .dragAndDropTarget(
                                    shouldStartDragAndDrop = { event ->
                                        event
                                                .mimeTypes()
                                                .contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                                    },
                                    target = remember {
                                        object : DragAndDropTarget {
                                            override fun onDrop(event: DragAndDropEvent): Boolean {
                                                val source =
                                                    event.toAndroidDragEvent().clipData?.getItemAt(0)?.text
                                                            .toString().toInt()
                                                println("Source is $source")
                                                println("Target is $index")
                                                println(components)

                                                components[source] = components[index]
                                                            .also {
                                                                components[index] = components[source]
                                                            }

                                                println(components)
                                                return true
                                            }
                                        }
                                    }
                                )
                    ) {
                        Text(
                            text = "$index $component",
                            fontSize = 40.sp,
                            color = Color.Black
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .background(Color.Green))
            }
        }
    }
}