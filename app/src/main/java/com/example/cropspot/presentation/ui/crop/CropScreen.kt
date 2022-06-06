package com.example.cropspot.presentation.ui.crop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cropspot.R
import com.example.cropspot.data.view.CropProfile


@Suppress("UnnecessaryVariable")
@Composable
fun CropScreen(
    onCropNameCollect: (String) -> Unit,
    model: CropScreenViewModel = hiltViewModel(),
) {
    val cropScreenState by model.screenState.collectAsState()
    when (val screenState = cropScreenState) {
        is CropScreenState.SUCCESS -> {
            val cropProfileState by screenState.profile.collectAsState(null)
            cropProfileState?.let {
                /*Log.d("AppDebug", "CropScreen: I called setAppBar")
                mainModel.setAppBar(it.name, true)*/
                onCropNameCollect(it.name)
                CropProfileScreen(crop = it)
            }
        }
        CropScreenState.LOADING -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
    }
}

@Composable
fun CropProfileScreen(crop: CropProfile) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(scrollState),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = crop.name,
                maxLines = 1,
                style = MaterialTheme.typography.h6,
            )
            if (!crop.isSupported) return@Row
            Icon(
                painter = painterResource(id = R.drawable.ic_verified),
                contentDescription = "Supported",
                tint = MaterialTheme.colors.secondary
            )
        }
        Text(
            text = crop.scientificName,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Text(
            text = crop.description,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.padding(20.dp))
    }
}