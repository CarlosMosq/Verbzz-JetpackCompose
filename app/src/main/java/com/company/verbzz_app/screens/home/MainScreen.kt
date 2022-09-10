package com.company.verbzz_app.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.components.BaseBackground
import com.company.verbzz_app.components.DrawerContent
import com.company.verbzz_app.components.MainBottomBar
import com.company.verbzz_app.navigation.ScreenList
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.company.verbzz_app.view_models.LanguageViewModel
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun MainScreen(
    navController: NavController,
    measurement: WindowMeasurement,
    languageViewModel: LanguageViewModel
) {
    val currentLanguage = remember {
        mutableStateOf("")
    }
    languageViewModel.getCurrentLanguage(currentLanguage)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

Scaffold(
    scaffoldState = scaffoldState,
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
        MainBottomBar(navController = navController, measurement = measurement)
    },
    drawerContent = {
        DrawerContent(measurement = measurement) { languageToSet ->
            languageViewModel.setCurrentLanguage(language = languageToSet, context = context)
            languageViewModel.getCurrentLanguage(languageState = currentLanguage)
            scope.launch { scaffoldState.drawerState.close() }
        }
    }
) {
    BaseBackground(
        measurement = measurement,
        title = stringResource(id = R.string.practice),
        secondIcon = false,
        hasTopBar = true,
        languageState = currentLanguage,
        openDrawer = {
            scope.launch { scaffoldState.drawerState.open() }
        },
        navController = navController,
        image = null,
        description = stringResource(id = R.string.description)
    ) {
        LessonsAndAds(navController = navController, measurement = measurement)
    }
}
//end of Scaffold

}

@Composable
fun LessonsAndAds(navController: NavController, measurement: WindowMeasurement) {
    Column {
        LessonLayout(navController = navController, measurement = measurement)
        AdView()
    }
}

@Composable
fun LessonLayout(navController: NavController, measurement: WindowMeasurement) {
    val itemsList = listOf(
        arrayOf(
            stringResource(id = R.string.conjugation),
            stringResource(id = R.string.search),
            Icons.Default.Search,
            ScreenList.ConjugateScreen.name
        ),
        arrayOf(
            stringResource(id = R.string.conjugationPractice),
            stringResource(id = R.string.conjugationPractice),
            Icons.Default.EditNote,
            ScreenList.GradedPracticeSetUp.name
        ),
        arrayOf(
            stringResource(id = R.string.translation),
            stringResource(id = R.string.translation),
            Icons.Default.Translate,
            ScreenList.TranslationPractice.name
        )
    )
    if(measurement.isPortrait) {
        LazyColumn(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            items(itemsList) { array ->
                LessonCard(
                    text = array[0].toString(),
                    description = array[1].toString(),
                    imageVector = array[2] as ImageVector,
                    measurement = measurement) {
                    navController.navigate(array[3].toString())
                }
            }
        }


    } else {
        LazyRow(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            items(itemsList) { array ->
                LessonCard(
                    text = array[0].toString(),
                    description = array[1].toString(),
                    imageVector = array[2] as ImageVector,
                    measurement = measurement) {
                    navController.navigate(array[3].toString())
                }
            }
        }
    }
}

@Composable
fun AdView() {
    //google ad structure
}

@Composable
fun LessonCard(
    text: String,
    imageVector: ImageVector,
    description: String,
    measurement: WindowMeasurement,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(25.dp),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        elevation = 6.dp,
        modifier = Modifier
            .padding(5.dp)
            .width(((measurement.smallest / 4) * 3).dp)
            .height((measurement.biggest / 8).dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = description,
                modifier = Modifier.size((measurement.biggest/15).dp)
            )
            Text(text = text)
        }

    }
}