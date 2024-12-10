package com.example.notecategories20.Note

data class CategoryClass(val name: String) {

    private val categories = mutableListOf<String>()

    fun addCategory(category: String) {
        categories.add(category)
    }

    fun removeCategory(category: String) {
        categories.remove(category)
    }

    fun getCategories(): List<String> {
        return categories

    }
}