package com.company.verbzz_app.screens.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.components.BaseBackground
import com.company.verbzz_app.components.DropdownMenuComponent
import com.company.verbzz_app.components.EmailAndPasswordLayout
import com.company.verbzz_app.components.SubmitButton
import com.company.verbzz_app.navigation.ScreenList
import com.company.verbzz_app.view_models.AuthViewModel
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.company.verbzz_app.utils.Constants.listOfLanguages

@ExperimentalComposeUiApi
@Composable
fun SignUpScreen(
    measurement: WindowMeasurement,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val selectedItem = remember {mutableStateOf("")}
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val context = LocalContext.current

    BaseBackground(
        measurement = measurement,
        title = stringResource(id = R.string.account),
        secondIcon = false,
        hasTopBar = false,
        navController = navController,
        image = null,
        description = stringResource(id = R.string.account)
    ) {
        SignUpForm(
            loading = false,
            validInputs = valid,
            isPortrait = measurement.isPortrait,
            email = email,
            password = password,
            selectedItem = selectedItem,
            passwordVisibility = passwordVisibility,
            passwordFocusRequest = passwordFocusRequest,
            measurement = measurement,
            listOfLanguages = listOfLanguages,
            keyboardController = keyboardController
        ) {email, password, language ->
            authViewModel.createUserWithEmailAndPassword(
                email = email,
                password = password,
                language = language,
                context = context) {
                navController.navigate(ScreenList.LoginScreen.name)
            }
        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun SignUpForm(
    loading: Boolean,
    validInputs: Boolean,
    isPortrait: Boolean,
    email: MutableState<String>,
    password: MutableState<String>,
    passwordVisibility: MutableState<Boolean>,
    passwordFocusRequest : FocusRequester,
    selectedItem: MutableState<String>,
    measurement: WindowMeasurement,
    listOfLanguages: List<String>,
    keyboardController: SoftwareKeyboardController?,
    onDone: (String, String, String) -> Unit = { _, _, _ ->}
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

        DropMenuLayout(
            measurement = measurement,
            listOfLanguages = listOfLanguages,
            loading = loading,
            validInputs = validInputs,
            email = email,
            password = password,
            keyboardController = keyboardController,
            selectedItem = selectedItem,
            onDone = onDone
        )

    }
}

@ExperimentalComposeUiApi
@Composable
fun DropMenuLayout(
    measurement: WindowMeasurement,
    listOfLanguages: List<String>,
    loading: Boolean,
    validInputs: Boolean,
    email: MutableState<String>,
    password: MutableState<String>,
    selectedItem: MutableState<String>,
    keyboardController: SoftwareKeyboardController?,
    onDone: (String, String, String) -> Unit
) {
    if(measurement.isPortrait) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LanguageMenuAndSubmit(
                measurement = measurement,
                listOfLanguages = listOfLanguages,
                loading = loading,
                validInputs = validInputs,
                keyboardController = keyboardController,
                email = email,
                password = password,
                selectedItem = selectedItem,
                onDone = onDone
            )
        }
    } else {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            LanguageMenuAndSubmit(
                measurement = measurement,
                listOfLanguages = listOfLanguages,
                loading = loading,
                validInputs = validInputs,
                keyboardController = keyboardController,
                email = email,
                password = password,
                selectedItem = selectedItem,
                onDone = onDone
            )
        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun LanguageMenuAndSubmit(
    measurement: WindowMeasurement,
    listOfLanguages: List<String>,
    loading: Boolean,
    validInputs: Boolean,
    keyboardController: SoftwareKeyboardController?,
    email: MutableState<String>,
    password: MutableState<String>,
    selectedItem: MutableState<String>,
    onDone: (String, String, String) -> Unit
) {
    DropdownMenuComponent(
        measurement = measurement,
        list = listOfLanguages,
        label = stringResource(id = R.string.chooseLanguage),
        selectedItem = selectedItem,
        description = stringResource(id = R.string.arrowIcon))

    SubmitButton(
        textId = stringResource(id = R.string.account),
        loading = loading,
        measurement = measurement,
        validInputs = validInputs) {
        onDone(email.value.trim(), password.value.trim(), selectedItem.value)
        keyboardController?.hide()
    }
}