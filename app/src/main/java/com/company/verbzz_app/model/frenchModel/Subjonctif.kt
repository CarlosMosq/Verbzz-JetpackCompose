package com.company.verbzz_app.model.frenchModel

import com.google.gson.annotations.SerializedName

data class Subjonctif(
    val imparfait: List<String>,
    val passé: List<String>,
    @SerializedName("plus-que-parfait")
    val plusQueParfait: List<String>,
    val présent: List<String>
)