package com.company.verbzz_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.navigation.ScreenList
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.google.firebase.auth.FirebaseAuth

@ExperimentalComposeUiApi
@Composable
fun TopAppBar(
    isConjugateScreen: Boolean,
    measurement: WindowMeasurement,
    navController: NavController,
    languageState: MutableState<String>,
    searchVerb: (String) -> Unit = {},
    openDrawer: () -> Unit = {}
) {
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val text = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(text.value) {
        text.value.trim().isNotEmpty()
    }

   TopAppBar(
       navigationIcon = {
           Image(
               painter = when(languageState.value) {
                   context.getString(R.string.english) ->
                       painterResource(id = R.drawable.english_language)
                   context.getString(R.string.french) ->
                       painterResource(id = R.drawable.french_language)
                   else -> painterResource(id = R.drawable.non_chosen_language)
               },
               contentDescription = stringResource(id = R.string.menuDrawer),
               modifier = Modifier
                   .height((measurement.biggest / 20).dp)
                   .padding(start = 10.dp)
                   .clip(RoundedCornerShape(10.dp))
                   .clickable {
                       openDrawer.invoke()
                   }
           )
       },
       title = { 
            if(!isConjugateScreen) {
                Text(text = "")
            }
           else {
               Row(horizontalArrangement = Arrangement.SpaceEvenly,
                   verticalAlignment = Alignment.CenterVertically,
                   modifier = Modifier.fillMaxWidth()
               ) {
                   InputField(
                       modifier = Modifier.width((measurement.width/2).dp),
                       valueState = text,
                       labelId = stringResource(id = R.string.enterVerb),
                       fontSize = 12.sp,
                       enabled = true,
                       measurement = measurement)
                   Icon(
                       modifier = Modifier.clickable {
                            if(valid) {
                                keyboardController!!.hide()
                                searchVerb.invoke(text.value)
                                text.value = ""
                            }
                       },
                       imageVector = Icons.Default.Search,
                       contentDescription = stringResource(id = R.string.search),
                       tint = MaterialTheme.colors.onSecondary
                   )
               }

               //search component
            }
       },               
       actions = {
           IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.options_menu),
                    tint = MaterialTheme.colors.onSecondary)
           }
           DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
               DropdownMenuItem(
                   onClick = {
                   FirebaseAuth.getInstance().signOut()
                   navController.navigate(ScreenList.AuthenticationScreen.name) },
                   modifier = Modifier.wrapContentHeight()
               ) {
                   Icon(imageVector = Icons.Default.Logout, contentDescription = "")
                   Text(text = stringResource(id = R.string.log_out))
               }
           }
       },
       backgroundColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.3f),
       modifier = Modifier.height(65.dp)
   )
}
