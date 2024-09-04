package com.karthee.interviewsample.presentation.ui

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.karthee.interviewsample.domain.models.SectionItemObj
import com.karthee.interviewsample.domain.models.SectionObj
import com.karthee.interviewsample.presentation.Screens
import com.karthee.interviewsample.presentation.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(navController: NavHostController) {
    val context = LocalContext.current as Activity
    val onNavigateBack = {
        if (navController.currentDestination == null || navController.currentDestination?.route == Screens.HOME.route)
            context.finish()
    }
    TopAppBar(
        title = {
            Text(text = "Sample App", fontSize = 18.sp, textAlign = TextAlign.Center)
        },
        navigationIcon = {
            IconButton(onClick = { onNavigateBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(),
        windowInsets = TopAppBarDefaults.windowInsets,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp)
    )
}

@Composable
fun MainScreen(navController: NavHostController) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val state = mainViewModel.uiFlow.collectAsState()
    val refresh: () -> Unit = {
        mainViewModel.refresh()
    }

    LaunchedEffect(Unit) {
        refresh()
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        CustomAppBar(navController)
        val status = state.value.uiStatus
        when (status) {
            UIStatus.Loading -> {
                LoadingBar()
            }

            is UIStatus.Error -> {
                val message =
                    if (status.code == -1001) status.msg else "Something went wrong. Please try again later"
                Error(message, onRefresh = refresh)
            }

            is UIStatus.Success -> {

                if (state.value.data.isNullOrEmpty()) {
                    Error("Currently data is not available...", false, {})
                } else {
                    SectionList(list = state.value.data!!)
                }

            }

            else -> {}
        }
    }
}

@Composable
fun ColumnScope.SectionList(list: List<SectionObj>) {
    LazyColumn {
        list.forEach { section ->
            when (section.sectionType) {
                SectionType.HORIZONTAL_FREE_SCROLL.type -> {
                    horizontalSection(section = section)
                }

                SectionType.BANNER.type -> {
                    banner(section = section)
                }

                SectionType.SPLIT_BANNER.type -> {
                    item {
                        SplitBanner(section)
                    }

                }
            }
        }
    }
}


fun LazyListScope.horizontalSection(section: SectionObj) {
    item {
        LazyRow {
            section.items.forEach { sectionItem ->
                item {
                    SectionItem(sectionItem, modifier = Modifier.size(124.dp))
                }

            }
        }
    }
}

@Composable
fun SectionItem(sectionItem: SectionItemObj, modifier: Modifier) {
    Column {
        LoadingImage(sectionItem.image, modifier = modifier)

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = sectionItem.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LoadingImage(image: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .border(BorderStroke(2.dp, SolidColor(Color.DarkGray)))
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(), contentDescription = "",
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray), contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Loading",
                        fontSize = 20.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray), contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No Image",
                        fontSize = 20.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

}

fun LazyListScope.banner(section: SectionObj) {

    section.items.forEach { sectionItem ->
        item {
            SectionItem(
                sectionItem, modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SplitBanner(section: SectionObj) {
    FlowRow(maxItemsInEachRow = 2, modifier = Modifier.fillMaxWidth()) {
        repeat(section.items.size) { index ->
            SectionItem(
                section.items[index], modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(240.dp)
            )
        }
    }
}


@Composable
fun LoadingBar() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun Error(msg: String = "", isActionNeeded: Boolean = true, onRefresh: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = msg)
        if (isActionNeeded) {
            Spacer(modifier = Modifier.height(14.dp))
            Button(onClick = { onRefresh() }) {
                Text(text = "Refresh")
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen(rememberNavController())
    }
}