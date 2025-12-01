package com.gg.aireader.ui.screens.model

enum class Mood(val tag: String) {
    HAPPY("happy"),
    SAD("sad"),
    CHILL("chill"),
    ENERGETIC("energetic"),
    DARK("dark"),
    ROMANTIC("romantic"),
    CALM("calm"),
    PARTY("party"),
    FOCUSED("focus");


    companion object {
        fun fromTag(tag: String): Mood? {
            return entries.firstOrNull {
                it.tag.equals(tag.trim(), ignoreCase = true)
            }
        }
    }

}

