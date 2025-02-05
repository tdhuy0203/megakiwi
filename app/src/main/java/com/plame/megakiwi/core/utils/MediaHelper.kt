package com.plame.megakiwi.core.utils

object MediaHelper {
    fun getImageUrl(path: String): String {
        return "https://cdn.plamestudio.com$path".lowercase()
    }

    fun getVideoUrl(path: String): String {
        return "https://cdn.plamestudio.com/video/$path"
    }
}