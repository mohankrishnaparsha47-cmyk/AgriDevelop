package com.example.agridevelop

// 1. Data mapping validation attributes matching firestore database document keys structure layout template
data class FarmingVideoItem(
    val videoTitle: String = "",
    val videoYoutubeUrl: String = "",
    val thumbnailAssetUrl: String = "",
    val channelName: String = "AgriDevelop Media"
)