package com.company.verbzz_app.screens.forgot

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.components.BaseBackground
import com.company.verbzz_app.components.EmailInput
import com.company.verbzz_app.components.SubmitButton
import com.company.verbzz_app.navigation.ScreenList
import com.company.verbzz_app.view_models.AuthViewModel
import com.company.verbzz_app.ui.theme.WindowMeasurement

@ExperimentalComposeUiApi
@Composable
fun ForgotPassword(
    measurement: WindowMeasurement,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val email = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value) {
        email.value.trim().isNotEmpty()
    }
    val context = LocalContext.current
    
    BaseBackground(
        measurement = measurement,
        title = stringResource(id = R.string.forgotAccountTitle),
        secondIcon = false,
        hasTopBar = false,
        navController = navController,
        image = null,
        description = stringResource(id = R.string.forgotAccountTitle)
    ) {
        ForgotForm(
            measurement = measurement,
            email = email,
            valid = valid,
            navController = navController,
            keyboardController = keyboardController
        ) { email ->
            authViewModel.sendEmailResetLink(email = email, context = context) {
                navController.navigate(ScreenList.LoginScreen.name)
            }
        }
    }
    
    
}

@ExperimentalComposeUiApi
@Composable
fun ForgotForm(
    measurement: WindowMeasurement, 
    email: MutableState<String>, 
    valid: Boolean,
    navController: NavController,
    keyboardController: SoftwareKeyboardController?,
    onDone: (String) -> Unit = { _ ->}
) {
    if(measurement.isPortrait) {
        Column (
            modifier = Modifier.fillMaxSize().padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ForgotComponents(
                email = email,
                measurement = measurement,
                valid = valid,
                keyboardController = keyboardController,
                navController = navController,
                onDone = onDone
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ForgotComponents(
                email = email,
                measurement = measurement,
                valid = valid,
                keyboardController = keyboardController,
                navController = navController,
                onDone = onDone
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun ForgotComponents(
    email: MutableState<String>,
    measurement: WindowMeasurement,
    valid: Boolean,
    keyboardController: SoftwareKeyboardController?,
    navController: NavController,
    onDone: (String) -> Unit
    ) {
    EmailInput(emailState = email, measurement = measurement)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SubmitButton(
            textId = stringResource(id = R.string.continueLink),
            loading = false,
            measurement = measurement,
            validInputs = valid) {
            onDone(email.value.trim())
            keyboardController?.hide()
        }
        Text(
            text = stringResource(id = R.string.back),
            modifier = Modifier
                .clickable { navController.navigate(ScreenList.LoginScreen.name) }
        )
    }

}