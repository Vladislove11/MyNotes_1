package com.example.mynotes_1

import java.io.Serializable

class Notes (
    val id: Int,
    val notes: String,
    var check: Int,
    val date: String
) : Serializable