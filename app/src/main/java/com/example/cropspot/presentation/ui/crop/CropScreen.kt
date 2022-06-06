package com.example.cropspot.presentation.ui.crop

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cropspot.R
import com.example.cropspot.domain.dto.CropProfileWithDiseases


@Suppress("UnnecessaryVariable")
@Composable
fun CropScreen(
    onCropNameCollect: (String) -> Unit,
    model: CropScreenViewModel = hiltViewModel(),
) {
    val cropScreenState by model.screenState.collectAsState()
    val cropDiseasesState by model.cropDiseases.collectAsState()
    when (val screenState = cropScreenState) {
        is CropScreenState.SUCCESS -> {
            val profileState by screenState.profile.collectAsState(null)
            profileState?.let {
                onCropNameCollect(it.profile.name)
                CropProfileScreen(crop = it, diseases = cropDiseasesState)
            }
        }
        CropScreenState.LOADING -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
    }
}

@Composable
fun CropProfileScreen(crop: CropProfileWithDiseases, diseases: List<String>) {
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
                text = crop.profile.name,
                maxLines = 1,
                style = MaterialTheme.typography.h6,
            )
            if (!crop.profile.isSupported) return@Row
            Icon(
                painter = painterResource(id = R.drawable.ic_verified),
                contentDescription = "Supported",
                tint = MaterialTheme.colors.secondary
            )
        }
        Text(
            text = crop.profile.scientificName,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Text(
            text = crop.profile.description,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.padding(20.dp))
        TextWithLinks(
            fullText = buildString {
                append(stringResource(id = R.string.ui_text_diseases))
                append(": ")
                append(diseases.joinToString())
            },
            linkText = diseases,
            onClick = {
                Log.d("AppDebug", "passed route home/crop/$it")
            },
        )
        Spacer(modifier = Modifier.padding(40.dp))
    }
}

@Composable
fun TextWithLinks(
    fullText: String,
    linkText: List<String>,
    onClick: (String) -> Unit,
) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colors.onSurface)) {
            append(fullText)
        }
        linkText.forEach { clickableText ->
            val startIndex = fullText.indexOf(clickableText)
            val endIndex = startIndex.plus(clickableText.length)
            addStyle(
                style = SpanStyle(color = MaterialTheme.colors.secondary),
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
        onClick = {
            annotatedString.getStringAnnotations("route", it, it)
                .firstOrNull()?.let { annotation ->
                    onClick(annotation.item)
                }
        },
        style = MaterialTheme.typography.body1,
    )
}