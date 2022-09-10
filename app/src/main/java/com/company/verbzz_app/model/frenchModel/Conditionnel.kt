package com.company.verbzz_app.model.frenchModel

import com.google.gson.annotations.SerializedName

data class Conditionnel(
    @SerializedName("passé 1ère forme")
    val passé1èreForme: List<String>,
    @SerializedName("passé 2ème forme")
    val passé2èmeForme: List<String>,
    val présent: List<String>
)