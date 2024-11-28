package com.example.notecategories20.Note

import java.util.Date

data class Note(
    var id: String?= null,
    var title: String? = null,
    var date: String? = null,
    var content: String? = null,
    var category: String? = null
){
    constructor(): this(null,null,null,null,null)
}

