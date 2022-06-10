package com.example.cropspot.presentation.ui.crop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cropspot.R
import com.example.cropspot.common.utils.ContentText
import com.example.cropspot.common.utils.LoadingBox
import com.example.cropspot.common.utils.ProfileTitle
import com.example.cropspot.common.utils.TextWithLinks
import com.example.cropspot.data.dto.CropProfileWithDiseases
import com.example.cropspot.presentation.ui.UiEvent
import kotlinx.coroutines.flow.collect


@Suppress("UnnecessaryVariable")
@Composable
fun CropScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onCropCollect: (String) -> Unit,
    model: CropScreenViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        model.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> onNavigate(uiEvent)
                else -> {}
            }
        }
    }

    val cropScreenState by model.screenState.collectAsState()
    val cropDiseases by model.cropDiseases.collectAsState()
    when (val screenState = cropScreenState) {
        CropScreenState.LOADING -> LoadingBox()
        is CropScreenState.SUCCESS -> {
            val profileState by screenState.profile.collectAsState(null)
            profileState?.let { crop ->
                onCropCollect(crop.profile.name)
                CropProfileScreen(
                    crop = crop,
                    diseases = cropDiseases,
                    onItemClick = { id ->
                        model.onEvent(CropScreenEvent.OnDiseaseClick(id))
                    }
                )
            }
        }
    }
}

@Composable
fun CropProfileScreen(
    crop: CropProfileWithDiseases,
    diseases: List<String>,
    onItemClick: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ProfileTitle(
            title = crop.profile.name,
            subtitle = crop.profile.scientificName,
            isIconShown = crop.profile.isSupported,
        )

        TextWithLinks(
            fullText = buildString {
                append(stringResource(id = R.string.ui_text_diseases))
                append(": ")
                append(diseases.joinToString())
            },
            linkText = diseases,
            onClick = { onItemClick(it) },
        )

        ContentText(text = crop.profile.description)

        Spacer(modifier = Modifier.padding(20.dp))
    }
}

