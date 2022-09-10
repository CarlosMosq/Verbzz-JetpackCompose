package com.company.verbzz_app.components

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.company.verbzz_app.utils.saveStatsToDatabase
import com.company.verbzz_app.view_models.AdViewModel
import com.company.verbzz_app.view_models.LanguageViewModel

@ExperimentalComposeUiApi
@Composable
fun BaseBackground(
    measurement: WindowMeasurement,
    title: String,
    secondIcon: Boolean,
    hasTopBar: Boolean,
    openDrawer: () -> Unit = {},
    navController: NavController,
    isConjugateScreen: Boolean = false,
    image: Painter? = null,
    description: String,
    tense: String = "",
    languageViewModel: LanguageViewModel? = null,
    context: Context? = null,
    verbCount: MutableState<Int>? = null,
    rightAnswers: MutableState<Int>? = null,
    languageState: MutableState<String>? = null,
    adViewModel: AdViewModel? = null,
    searchVerb: (String) -> Unit = {},
    content: @Composable () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.honey_bee_flower),
                        contentDescription = stringResource(id = R.string.bee),
                        modifier = Modifier.height((measurement.height/4).dp),
                        contentScale = ContentScale.FillWidth
                    )
                    Surface(modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth(),
                        color = MaterialTheme.colors.background,
                        shape = RoundedCornerShape(
                            topStart = 25.dp,
                            topEnd = 25.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp)
                    ) {}
                }
                if(hasTopBar) {
                    TopAppBar(
                        isConjugateScreen = isConjugateScreen,
                        measurement = measurement,
                        languageState = languageState!!,
                        navController = navController,
                        searchVerb = searchVerb
                    ) {
                        openDrawer.invoke()
                    }
                }
            }


            Surface(
                modifier = Modifier
                    .height(((measurement.height / 4) * 3 + 10).dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colors.background,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    TopRowTitle(
                        measurement = measurement,
                        title = title,
                        description = description,
                        secondIcon = secondIcon,
                        image = image,
                        languageViewModel = languageViewModel,
                        context = context,
                        verbCount = verbCount,
                        rightAnswers = rightAnswers,
                        languageState = languageState,
                        adViewModel = adViewModel,
                        tense = tense
                    )
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.onPrimary
                    ) {
                        content.invoke()
                    }

                }
            }

        }

    }
}

@Composable
fun TopRowTitle(
    measurement: WindowMeasurement,
    title: String,
    description: String,
    secondIcon: Boolean,
    tense: String = "",
    image: Painter?,
    languageViewModel: LanguageViewModel?,
    context: Context?,
    verbCount: MutableState<Int>?,
    rightAnswers: MutableState<Int>?,
    languageState: MutableState<String>?,
    adViewModel: AdViewModel?
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_nicubunu_honey),
            contentDescription = stringResource(id = R.string.description),
            modifier = Modifier.size((measurement.biggest/15).dp).clickable {
                if(secondIcon) {
                    saveStatsToDatabase(
                        languageViewModel = languageViewModel!!,
                        verbCount = verbCount!!,
                        rightAnswers = rightAnswers!!,
                        languageState = languageState!!,
                        tense = tense
                    )
                    adViewModel!!.showAd(activity = context as Activity)
                }
            }
        )
        Text(
            text = title,
            modifier = Modifier.padding(1.dp),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onSecondary,
            maxLines = 2
        )
        if(secondIcon) {
            Image(
                painter = image!!,
                contentDescription = description,
                modifier = Modifier.size((measurement.biggest/15).dp).clickable {
                    saveStatsToDatabase(
                        languageViewModel = languageViewModel!!,
                        verbCount = verbCount!!,
                        rightAnswers = rightAnswers!!,
                        languageState = languageState!!,
                        tense = tense
                    )
                    adViewModel!!.showAd(activity = context as Activity)
                }
            )
        }
        else {
            Box(modifier = Modifier.size((measurement.biggest/15).dp))
        }
    }
}

