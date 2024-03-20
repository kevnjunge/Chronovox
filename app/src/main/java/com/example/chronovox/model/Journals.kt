package com.example.chronovox.model

import com.google.firebase.Timestamp

data class Journals(
    val userId:String = "",
    val journalEntry:String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val documentId: String = ""
)
