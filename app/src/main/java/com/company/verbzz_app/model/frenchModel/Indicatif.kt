package com.company.verbzz_app.model.frenchModel

import com.google.gson.annotations.SerializedName

data class Indicatif(
    @SerializedName("futur antérieur")
    val futurAntérieur: List<String>,
    @SerializedName("futur simple")
    val futurSimple: List<String>,
    val imparfait: List<String>,
    @SerializedName("passé antérieur")
    val passéAntérieur: List<String>,
    @SerializedName("passé composé")
    val passéComposé: List<String>,
    @SerializedName("passé simple")
    val passéSimple: List<String>,
    @SerializedName("plus-que-parfait")
    val plusQueParfait: List<String>,
    val présent: List<String>
)