package com.example.cropspot.presentation.ui.crop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cropspot.common.utils.LoadingBox
import com.example.cropspot.R
import com.example.cropspot.common.utils.ContentText
import com.example.cropspot.common.utils.SubtitleText
import com.example.cropspot.common.utils.TitleText
import com.example.cropspot.data.view.CropProfileWithDiseases
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
    val cropDiseasesState by model.cropDiseases.collectAsState()
    when (val screenState = cropScreenState) {
        CropScreenState.LOADING -> LoadingBox()
        is CropScreenState.SUCCESS -> {
            val profileState by screenState.profile.collectAsState(null)
            profileState?.let { crop ->
                onCropCollect(crop.profile.name)
                CropProfileScreen(
                    crop = crop,
                    diseases = cropDiseasesState,
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
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                TitleText(text = crop.profile.name)
                if (!crop.profile.isSupported) return@Row
                Icon(
                    painter = painterResource(id = R.drawable.ic_verified),
                    contentDescription = "Supported",
                    tint = MaterialTheme.colors.primary
                )
            }
            SubtitleText(text = crop.profile.scientificName)
        }

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

@Composable
fun TextWithLinks(
    fullText: String,
    linkText: List<String>,
    onClick: (String) -> Unit,
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold,
            )
        ) { append(fullText) }

        linkText.forEach { clickableText ->
            val startIndex = fullText.indexOf(clickableText)
            val endIndex = startIndex.plus(clickableText.length)
            addStyle(
                style = SpanStyle(MaterialTheme.colors.secondaryVariant),
                start = startIndex,
                end = endIndex,
            )
            addStringAnnotation(
                tag = "route",
                annotation = clickableText,
                start = startIndex,
                end = endIndex,
            )
        }
    }
    ClickableText(
        modifier = Modifier.fillMaxWidth(),
        text = annotatedString,
        onClick = { offset ->
            annotatedString
                .getStringAnnotations("route", offset, offset)
                .firstOrNull()?.let { onClick(it.item) }
        },
        style = MaterialTheme.typography.body1,
    )
}