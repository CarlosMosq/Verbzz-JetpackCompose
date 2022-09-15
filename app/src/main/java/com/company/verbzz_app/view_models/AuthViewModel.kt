package com.company.verbzz_app.view_models

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.verbzz_app.R
import com.company.verbzz_app.repository.FireRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: FireRepository) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _loading = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(
        email: MutableState<String>,
        password: MutableState<String>,
        languageState: MutableState<String>,
        context: Context, 
        goToMain: () -> Unit
    )
    = viewModelScope.launch {
        try {
            auth
                .signInWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful) {
                        val user = task.result.user
                        if (user!!.isEmailVerified) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.accessGranted),
                                Toast.LENGTH_SHORT).show()
                            repository.getCurrentLanguage(languageState, user.uid)
                            goToMain()
                        }
                        else {
                            user.sendEmailVerification().addOnSuccessListener {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.verifyEmail),
                                    Toast.LENGTH_LONG).show()
                            }
                            email.value = ""
                            password.value = ""
                            Toast.makeText(
                                context,
                                context.getString(R.string.verifySent),
                                Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.errorLinkSent),
                            Toast.LENGTH_SHORT).show()
                        Log.d("SIGN_IN ERROR", "Error with Firebase Sign In Method")
                    }
                }
        } catch (ex: Exception) {
            Log.e("ERROR", "Exception thrown when creating user ${ex.message}")
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        language: String,
        context: Context,
        loginToAssert: () -> Unit
    ) = viewModelScope.launch {
        try {
            if(_loading.value == false) {
                _loading.value = true
                auth
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful) {
                            val newUser = task.result.user?.uid
                            Toast.makeText(
                                context,
                                context.getString(R.string.accountCreated),
                                Toast.LENGTH_SHORT).show()
                            repository.setCurrentLanguage(
                                language = language,
                                context = context,
                                userID = newUser!!
                            )
                            loginToAssert()
                        }
                        else {
                            Toast.makeText(
                                context,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT).show()
                            loginToAssert()
                            Log.d("SIGNUP ERROR", "Error with Firebase Sign Up Method")
                        }
                        _loading.value = false
                    }
            }
        } catch (ex: Exception) {
            Log.e("ERROR", "Exception thrown when creating user ${ex.message}")
        }

    }

    fun sendEmailResetLink(email: String, context: Context, loginToAssert: () -> Unit)
    = viewModelScope.launch {
        try {
            auth.sendPasswordResetEmail(email).addOnCompleteListener {task ->
                if(task.isSuccessful) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.forgotLinkSent),
                        Toast.LENGTH_SHORT).show()
                    loginToAssert()
                }
                else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.tryAgain),
                        Toast.LENGTH_SHORT).show()
                    loginToAssert()
                }
            }
        } catch (ex: Exception) {
            Log.e("RESET_ERROR", "Error to send reset link to e-mail: $ex")
        }
    }


}