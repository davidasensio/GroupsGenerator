package com.handysparksoft.groupsgenerator.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.handysparksoft.groupsgenerator.model.AList

class Prefs(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var aLists: List<AList>
        get() {
            val serializedValue = prefs.getString(A_LISTS_KEY, "")
            val type = object : TypeToken<List<AList>>() {}.type
            return Gson().fromJson(serializedValue, type) ?: emptyList()
        }
        set(value) {
            val serializedValue = Gson().toJson(value)
            prefs.edit().putString(A_LISTS_KEY, serializedValue).apply()
        }

    companion object {
        private const val PREFS_NAME = "groups_generator"
        private const val A_LISTS_KEY = "aLists"
    }
}
