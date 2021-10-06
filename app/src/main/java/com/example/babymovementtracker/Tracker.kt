package com.example.babymovementtracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.babymovementtracker.data.Record
import com.example.babymovementtracker.ui.theme.Pink100
import com.example.babymovementtracker.ui.theme.Pink200
import com.example.babymovementtracker.ui.theme.Pink50
import com.example.babymovementtracker.util.viewModelProviderFactoryOf
import com.example.jetcaster.data.room.DateTimeTypeConverters
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun Tracker() {
    val context = LocalContext.current
    val viewModel: TrackerViewModel = viewModel(factory = viewModelProviderFactoryOf {
        TrackerViewModel(context)
    })
    val viewState by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.title),
                    fontWeight = FontWeight.ExtraBold
                )
            },
        )
        Column(
            modifier = Modifier.fillMaxHeight(1F).background(Color.White)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(.5f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Pink200
                    ),
                    onClick = {
                        (viewModel::softKick)()
                        coroutineScope.launch {
                            delay(10L)
                            listState.scrollToItem(0, 0)
                        }
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(168.dp)
                ) {
                    Text(text = "KICK", fontSize = 50.sp)
                }
            }

            RecList(viewState.records, listState)

        }

    }
}


@Composable
fun RecList(recs: List<Record>, listState: LazyListState = rememberLazyListState()) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(9.dp),

        ) {
        items(recs, key = { it.uid }) { item ->
            DateTimeTypeConverters.fromLocalDateTime2(item.tapped)?.let {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Face,
                        contentDescription = "little baby",
                        tint = Pink200
                    )
                    Text(text = it,
                        color = Color(0x26, 0x2A, 0x53),
                        fontFamily = FontFamily.Monospace)
                }

            }
        }
    }
}