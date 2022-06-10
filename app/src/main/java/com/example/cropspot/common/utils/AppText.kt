package com.example.cropspot.common.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.cropspot.R

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun SubtitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle2,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle1,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun ContentText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Justify,
    )
}

@Composable
fun ProfileTitle(title: String, subtitle: String, isIconShown: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TitleText(text = title)
        if (!isIconShown) return@Row
        Icon(
            painter = painterResource(id = R.drawable.ic_verified),
            contentDescription = "Supported",
            tint = MaterialTheme.colors.primary
        )
    }
    SubtitleText(text = subtitle)
}

@Composable
fun TitledParagraph(title: String, content: String) {
    HeaderText(text = title)
    ContentText(text = content)
}

@Composable
fun UnorderedList(title: String, items: List<String>) {
    HeaderText(text = title)
    items.forEach { UnorderedListItem(text = it) }
}

@Composable
fun UnorderedListItem(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
        ContentText(text = text)
    }
}

@Composable
fun LoadingBox() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { CircularProgressIndicator() }
}

@Composable
fun TextWithLinks(
    fullText: String,
    linkText: List<String>,
    annotations: List<String>? = null,
    onClick: (annotation: String) -> Unit,
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold,
            )
        ) { append(fullText) }

        linkText.forEachIndexed { index, clickableText ->
            val startIndex = fullText.indexOf(clickableText)
            val endIndex = startIndex.plus(clickableText.length)
            addStyle(
                style = SpanStyle(MaterialTheme.colors.secondaryVariant),
                start = startIndex,
                end = endIndex,
            )
            addStringAnnotation(
                tag = "route",
                annotation = if (annotations != null && annotations.size == linkText.size) {
                    annotations[index]
                } else clickableText,
                start = startIndex,
                end = endIndex,
            )
        }
    }
    ClickableText(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.body1
            .copy(textAlign = TextAlign.Justify),
        text = annotatedString,
        onClick = { offset ->
            annotatedString
                .getStringAnnotations("route", offset, offset)
                .firstOrNull()?.let { onClick(it.item) }
        },
    )
}