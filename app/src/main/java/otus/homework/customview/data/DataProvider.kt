package otus.homework.customview.data

import android.content.Context
import com.google.gson.Gson
import otus.homework.customview.R

class DataProvider(private val context: Context) {

    private val gson: Gson = Gson()

    fun getExpenses(category: String? = null): List<Expense> = try {
        val gsonString = context.resources.openRawResource(R.raw.payload).bufferedReader().use { it.readText() }
        gson.fromJson(gsonString, Array<Expense>::class.java).toList().let { expenses ->
            category?.let {
                expenses.filter { it.category == category }
            } ?: expenses
        }
    } catch (e: Throwable) {
        listOf()
    }
}