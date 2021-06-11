package com.evanisnor.flickwatcher.ux.composable

import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect

/**
 * Disable the fling behavior of a composable
 */
internal fun flingDisabled(): FlingBehavior = object : FlingBehavior {
    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        return 0f
    }
}

/**
 * Multi-orientation lazy list that ratchets between list items as you swipe.
 */
@Composable
fun <T> RatchetScrollList(
    modifier: Modifier = Modifier,
    items: List<T>,
    lazyListState: LazyListState = rememberLazyListState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable (index: Int, orientation: Int, item: T, modifier: Modifier) -> Unit
) {
    val configuration = LocalConfiguration.current
    var orientation by remember { mutableStateOf(configuration.orientation) }

    // Remember the size of list items so we can scroll by the number of pixels that exist between
    // each item
    var itemWidth by remember { mutableStateOf(0f) }
    var itemHeight by remember { mutableStateOf(0f) }

    // React to Configuration changes to capture current orientation
    LaunchedEffect(configuration, coroutineScope) {
        snapshotFlow {
            configuration.orientation
        }.collect { latestOrientation ->
            orientation = latestOrientation
        }
    }

    when {
        orientation.isPortrait() -> {
            // For portrait orientation, use LazyColumn and vertical draggable
            LazyColumn(
                modifier = modifier,
                state = lazyListState,
                flingBehavior = flingDisabled()
            ) {
                itemsIndexed(items) { index, item ->
                    content(
                        index = index,
                        orientation = orientation,
                        item = item,
                        modifier = Modifier
                            .draggable(
                                state = rememberDraggableState {},
                                orientation = Orientation.Vertical,
                                onDragStopped = { velocity ->
                                    when {
                                        // Ratchet to next item
                                        velocity < 0 -> lazyListState.animateScrollBy(itemHeight)
                                        // Ratchet to previous item
                                        velocity > 0 -> lazyListState.animateScrollBy(itemHeight * -1)
                                    }
                                })
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)

                                itemWidth = placeable.width.toFloat()
                                itemHeight = placeable.height.toFloat()

                                layout(placeable.width, placeable.height) {
                                    placeable.placeRelative(0, 0)
                                }
                            })
                }
            }
        }

        // For landscape orientation, use LazyRow and horizontal draggable
        orientation.isLandscape() -> {
            LazyRow(
                modifier = modifier,
                state = lazyListState,
                flingBehavior = flingDisabled()
            ) {
                itemsIndexed(items) { index, item ->
                    content(
                        index = index,
                        orientation = orientation,
                        item = item,
                        modifier = Modifier
                            .draggable(
                                state = rememberDraggableState {},
                                orientation = Orientation.Horizontal,
                                onDragStopped = { velocity ->
                                    when {
                                        // Ratchet to next item
                                        velocity < 0 -> lazyListState.animateScrollBy(itemWidth)
                                        // Ratchet to previous item
                                        velocity > 0 -> lazyListState.animateScrollBy(itemWidth * -1)
                                    }
                                })
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)

                                itemWidth = placeable.width.toFloat()
                                itemHeight = placeable.height.toFloat()

                                layout(placeable.width, placeable.height) {
                                    placeable.placeRelative(0, 0)
                                }
                            })
                }
            }
        }
    }

}