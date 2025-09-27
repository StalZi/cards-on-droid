@file:OptIn(ExperimentalFoundationApi::class)

package com.stalzi.patientcards

import android.content.ClipData
import android.content.ClipDescription
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel(), onNavigateToCard: (Int, String) -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addCard() }) {
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

            itemsIndexed(viewModel.cardsList) { index, card ->
                key(card.image, card.label) {
                    Card(
                        shape = RectangleShape,
                        modifier = Modifier
                                .height(80.dp)
                                .fillMaxWidth()
                                .dragAndDropSource {
                                    detectTapGestures(
                                        onLongPress = {
                                            startTransfer(
                                                transferData = DragAndDropTransferData(
                                                    clipData = ClipData.newPlainText(
                                                        "text",
                                                        "$index",
                                                    )
                                                )
                                            )
                                        }
                                    )
                                }
                                .dragAndDropTarget(shouldStartDragAndDrop = { event ->
                                    event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                                }, target = remember {
                                    object : DragAndDropTarget {
                                        override fun onDrop(event: DragAndDropEvent): Boolean {
                                            val source =
                                                event.toAndroidDragEvent().clipData?.getItemAt(0)?.text.toString()
                                                        .toInt()
                                            println("Source is $source")
                                            println("Target is $index")
                                            if(source == index){return false}

                                            viewModel.swapCards(source, index)

                                            return true
                                        }
                                    }
                                })
                    ) {
                        Row(modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Row(
                                modifier = Modifier
                                        .clickable(onClick = {onNavigateToCard(card.image, card.label)})
                                        .fillMaxSize()
                                        .weight(7f)
                            ) {
                                Image(
                                    painter = painterResource(card.image),
                                    contentDescription = "No image"
                                )

                                Text(
                                    text = "$index ${card.label}",
                                    fontSize = 40.sp,
                                    color = Color.Black
                                )
                            }
                            Icon(
                                painter = painterResource(R.drawable.baseline_drag_indicator_24),
                                contentDescription = "Draggable",
                                modifier = Modifier
                                        .weight(1f)
                                        .align(Alignment.CenterVertically)
                                        .fillMaxSize()
                                        .background(Color.Red)
                            )
                        }
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
