package com.example.servicesandbrexample.model.entities

data class Description(
    val text: String = "sdgs",
    val pos: String = "sdfsf",
    val tr: ArrayList<Translation> = getNullTranslation()
)

fun getNullTranslation(): ArrayList<Translation> {
    return arrayListOf(Translation("fdfgsdfg", "bdfgdfg"))
}
