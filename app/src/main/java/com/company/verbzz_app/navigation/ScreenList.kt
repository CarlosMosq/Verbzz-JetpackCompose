package com.company.verbzz_app.navigation

enum class ScreenList {
    SplashScreen,
    AuthenticationScreen,
    LoginScreen,
    SignUpScreen,
    ForgotPassword,
    MainScreen,
    ConjugateScreen,
    GradedPracticeSetUp,
    GradedPracticeScreen,
    StatisticsScreen,
    ShoppingScreen,
    TranslationPractice;

    companion object {
        fun fromRoute(route: String?) : ScreenList
        = when(route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            AuthenticationScreen.name -> AuthenticationScreen
            LoginScreen.name -> LoginScreen
            SignUpScreen.name -> SignUpScreen
            ForgotPassword.name -> ForgotPassword
            MainScreen.name -> MainScreen
            ConjugateScreen.name -> ConjugateScreen
            GradedPracticeSetUp.name -> GradedPracticeSetUp
            GradedPracticeScreen.name -> GradedPracticeScreen
            StatisticsScreen.name -> StatisticsScreen
            ShoppingScreen.name -> ShoppingScreen
            TranslationPractice.name -> TranslationPractice
            null -> MainScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }

}