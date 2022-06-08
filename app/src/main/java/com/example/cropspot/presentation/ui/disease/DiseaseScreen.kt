package com.example.cropspot.presentation.ui.disease

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cropspot.common.utils.LoadingBox
import com.example.cropspot.R
import com.example.cropspot.common.utils.*
import com.example.cropspot.data.view.DiseaseProfileWithInfoAndCrops

@Suppress("UnnecessaryVariable")
@Composable
fun DiseaseScreen(
    onDiseaseCollect: (String) -> Unit,
    model: DiseaseScreenViewModel = hiltViewModel(),
) {
    val diseaseScreenState by model.screenState.collectAsState()
    when (val screenState = diseaseScreenState) {
        DiseaseScreenState.LOADING -> LoadingBox()
        is DiseaseScreenState.SUCCESS -> {
            val profileState by screenState.profile.collectAsState(null)
            profileState?.let {
                onDiseaseCollect(it.profile.id)
                DiseaseProfileScreen(it)
            }
        }
    }
}

@Composable
fun DiseaseProfileScreen(disease: DiseaseProfileWithInfoAndCrops) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 12.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TitleText(text = disease.profile.id)
                if (!disease.profile.isSupported) return@Row
                Icon(
                    painter = painterResource(id = R.drawable.ic_verified),
                    contentDescription = "Supported",
                    tint = MaterialTheme.colors.primary
                )
            }
            SubtitleText(text = disease.profile.pathogen)
        }

        Column {
            HeaderText(text = stringResource(id = R.string.ui_text_symptoms))
            disease.symptoms.forEach { BulletListItem(text = it.value) }
        }

        Column {
            HeaderText(text = stringResource(id = R.string.ui_text_treatments))
            disease.treatments.forEach { BulletListItem(text = it.value) }
        }

        Column {
            HeaderText(text = stringResource(id = R.string.ui_text_cause))
            ContentText(text = disease.profile.cause)
        }

        /*TextWithLinks(
            fullText = buildString {
                append(stringResource(id = R.string.ui_text_crops))
                append(": ")
                append(diseases.joinToString())
            },
            linkText = diseases,
            onClick = { onItemClick(it) },
        )*/

        Spacer(modifier = Modifier.padding(20.dp))
    }
}

