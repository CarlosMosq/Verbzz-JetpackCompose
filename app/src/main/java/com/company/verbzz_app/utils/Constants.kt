package com.company.verbzz_app.utils

object Constants {

    const val BASE_URL = "https://raw.githubusercontent.com/"

    const val ENGLISH_URL = "CarlosMosq/English-Verbs-Conjugates/master/verbs-conjugations.json"

    const val FRENCH_URL = "CarlosMosq/Verbes-Francais-Conjugues/master/verbs_without_signs.json"

    val nbrOfVerbs = listOf("10", "20", "30", "40", "50", "100")

    val englishTenses = listOf(
        "Present", "Past", "Past (irr)", "Future", "Present Perfect", "Past Perfect", "Future Perfect"
        , "Present Conditional", "Perfect Conditional", "Present Subjunctive", "Perfect Subjunctive"
    )
    val frenchTenses = listOf(
        "Tous", "Présent (-er)", "Présent (-ir)", "Présent (-re)"
        , "Présent (Réguliers)", "Présent (Irréguliers)", "Présent (Réfléchis)"
        , "Présent (Tous)", "Imparfait", "Futur Simple", "Passé Simple", "Passé Composé (avoir)"
        , "Passé Composé (être)", "Passé Composé (Réfléchis)", "Passé Composé (Tous)"
        , "Plus-Que-Parfait", "Futur Antérieur", "Passé Antérieur", "Présent Conditionnel"
        , "Présent Subjonctif","Passé Subjonctif", "Imparfait Subjonctif", "Plus-que-parfait Subjonctif"
    )

    val englishMoods = listOf(
        "Indicative", "Conditional", "Subjunctive", "Imperative", "Participle", "Gerund", "Infinitive"
    )

    val frenchMoods = listOf(
        "Indicatif", "Conditionnel", "Subjonctif", "Impératif", "Participe", "Infinitif"
    )

    val listOfLanguages = listOf("English", "Français")

}