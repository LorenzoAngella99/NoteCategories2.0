package com.example.notecategories20.Note.Tag

data class TagCategory(
    var name: String?= null,
    var color: String?= null
){
    constructor(): this(null,null)
}

