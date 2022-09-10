package com.company.verbzz_app.model.englishModel

data class EnglishModelItem(
    val conditional: Conditional,
    val gerund: List<String>,
    val imperative: List<String>,
    val indicative: Indicative,
    val infinitive: List<String>,
    val participle: List<String>,
    val subjuntive: Subjuntive,
    val verb: String
)