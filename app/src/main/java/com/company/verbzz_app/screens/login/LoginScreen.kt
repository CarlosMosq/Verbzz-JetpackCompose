package com.company.verbzz_app.screens.login

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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.components.BaseBackground
import com.company.verbzz_app.components.EmailAndPasswordLayout
import com.company.verbzz_app.components.SubmitButton
import com.company.verbzz_app.navigation.ScreenList
import com.company.verbzz_app.view_models.AuthViewModel
import com.company.verbzz_app.ui.theme.WindowMeasurement

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    measurement: WindowMeasurement,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val context = LocalContext.current

    BaseBackground(
        measurement = measurement,
        title = stringResource(id = R.string.log_in),
        secondIcon = false,
        hasTopBar = false,
        navController = navController,
        image = null,
        description = stringResource(id = R.string.log_in)) {
        LoginForm(
            isPortrait = measurement.isPortrait,
            loading = false,
            email = email,
            password = password,
            measurement = measurement,
            navController = navController,
            passwordVisibility = passwordVisibility,
            passwordFocusRequest = passwordFocusRequest,
            validInputs = valid,
            keyboardController = keyboardController
        ) { email, password ->
            authViewModel.signInWithEmailAndPassword(
                email = email,
                password = password,
                context = context) {
                navController.navigate(ScreenList.MainScreen.name)
            }
        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun LoginForm(loading: Boolean,
              validInputs: Boolean,
              isPortrait: Boolean,
              email: MutableState<String>,
              password: MutableState<String>,
              passwordVisibility: MutableState<Boolean>,
              passwordFocusRequest : FocusRequester,
              measurement: WindowMeasurement,
              navController: NavController,
              keyboardController: SoftwareKeyboardController?,
              onDone: (MutableState<String>, MutableState<String>) -> Unit = { _, _ ->}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        EmailAndPasswordLayout(
            isPortrait = isPortrait,
            loading = loading,
            email = email,
            password = password,
            measurement = measurement,
            passwordVisibility = passwordVisibility,
            passwordFocusRequest = passwordFocusRequest
        )
        SubmitButton(
            textId = stringResource(id = R.string.log_in),
            loading = loading,
            measurement = measurement,
            validInputs = validInputs) {
            email.value.trim()
            password.value.trim()
            onDone(email, password)
            keyboardController?.hide()
        }
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.noAccount),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { navController.navigate(ScreenList.SignUpScreen.name) }
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = stringResource(id = R.string.forgotAccountTitle),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { navController.navigate(ScreenList.ForgotPassword.name) }
            )
        }

    }
}

