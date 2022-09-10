package com.company.verbzz_app.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.company.verbzz_app.R
import com.company.verbzz_app.ui.theme.WindowMeasurement

@ExperimentalComposeUiApi
@Composable
fun EmailAndPasswordLayout(
    isPortrait: Boolean,
    loading: Boolean,
    measurement: WindowMeasurement,
    email: MutableState<String>,
    password: MutableState<String>,
    passwordVisibility: MutableState<Boolean>,
    passwordFocusRequest : FocusRequester
) {

    if(isPortrait) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            EmailInput(
                emailState = email,
                measurement = measurement,
                onAction = KeyboardActions{
                    passwordFocusRequest.requestFocus()
                })
            PasswordInput(
                modifier = Modifier.focusRequester(passwordFocusRequest),
                passwordState = password,
                labelId = stringResource(id = R.string.password),
                enabled = !loading,
                measurement = measurement,
                passwordVisibility = passwordVisibility,
            )
        }
    }
    else {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
            ) {
            EmailInput(
                emailState = email,
                measurement = measurement,
                onAction = KeyboardActions{
                    passwordFocusRequest.requestFocus()
                })
            PasswordInput(
                modifier = Modifier.focusRequester(passwordFocusRequest),
                passwordState = password,
                labelId = stringResource(id = R.string.password),
                enabled = !loading,
                measurement = measurement,
                passwordVisibility = passwordVisibility,
            )
        }
    }



}

