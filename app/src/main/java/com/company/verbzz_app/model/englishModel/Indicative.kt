package com.company.verbzz_app.model.englishModel

import com.google.gson.annotations.SerializedName

data class Indicative(
    val future: List<String>,
    val imperfect: List<String>,
    val perfect: List<String>,
    val plusperfect: List<String>,
    val present: List<String>,
    @SerializedName("previous future")
    val previousFuture: List<String>
)