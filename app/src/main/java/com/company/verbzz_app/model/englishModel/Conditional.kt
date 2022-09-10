package com.company.verbzz_app.model.englishModel

import com.google.gson.annotations.SerializedName

data class Conditional(
    val conditional: List<String>,
    @SerializedName("conditional perfect")
    val conditionalPerfect: List<String>
)