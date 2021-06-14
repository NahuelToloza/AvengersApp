package com.toloza.avengersapp.extensions

fun String.replaceHttpForHttps(): String {
    return replace("http://", "https://")
}

fun String.findBetweenParenthesis(): String {
    return substringAfter("(").substringBefore(')')
}