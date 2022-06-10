package com.example.cropspot.presentation.ui.disease

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
import com.example.cropspot.common.utils.*
import com.example.cropspot.data.view.DiseaseProfile
import com.example.cropspot.presentation.ui.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

@Suppress("UnnecessaryVariable")
@Composable
fun DiseaseScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onDiseaseCollect: (String) -> Unit,
    model: DiseaseScreenViewModel = hiltViewModel(),
) {
    val diseaseScreenState by model.screenState.collectAsState()
    val cropIds by model.diseaseCropIds.collectAsState()
    model.loadLocalizedNames(cropIds)
    val cropNames by model.diseaseCropNames.collectAsState()
    when (val screenState = diseaseScreenState) {
        DiseaseScreenState.LOADING -> LoadingBox()
        is DiseaseScreenState.SUCCESS -> {
            val profileState by screenState.profile.collectAsState(null)
            profileState?.let { disease ->
                onDiseaseCollect(disease.profile.info.id)
                DiseaseProfileScreen(
                    disease = disease.profile.info,
                    cropIds = cropIds,
                    cropNames = cropNames,
                    symptomsFlow = disease.symptoms,
                    treatmentsFlow = disease.treatments,
                ) {
                    model.onEvent(DiseaseScreenEvent.OnCropClick(it))
                }
            }
        }
    }
    LaunchedEffect(key1 = true) {
        model.uiEvent.collect { uiEvent ->
            if (uiEvent is UiEvent.Navigate) onNavigate(uiEvent)
        }
    }
}

@Composable
fun DiseaseProfileScreen(
    disease: DiseaseProfile,
    cropIds: List<String>,
    cropNames: List<String>,
    symptomsFlow: Flow<List<String>>,
    treatmentsFlow: Flow<List<String>>,
    onItemClick: (id: String) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 12.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ProfileTitle(
            title = disease.id,
            subtitle = disease.pathogen,
            isIconShown = disease.isSupported,
        )

        TextWithLinks(
            fullText = buildString {
                append(stringResource(id = R.string.ui_text_crops))
                append(": ")
                append(cropNames.joinToString())
            },
            linkText = cropNames,
            annotations = if (cropIds.size == cropNames.size) cropIds else null,
            onClick = { onItemClick(it) },
        )

        val symptoms by symptomsFlow.collectAsState(initial = emptyList())
        UnorderedList(
            title = stringResource(id = R.string.ui_text_symptoms),
            items = symptoms,
        )

        val treatments by treatmentsFlow.collectAsState(initial = emptyList())
        UnorderedList(
            title = stringResource(id = R.string.ui_text_treatments),
            items = treatments,
        )

        TitledParagraph(
            title = stringResource(id = R.string.ui_text_cause),
            content = disease.cause,
        )

        Spacer(modifier = Modifier.padding(20.dp))
    }
}

