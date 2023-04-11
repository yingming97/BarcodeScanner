package pham.hien.barcodescanner

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharePre {
    private val PREFERENCES_NAME = "BarCode"

    fun setBankAccountRecent(context: Context, value: BankAccountRecent) {
        val sharePref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharePref.edit()
        val gson = Gson()
        val model = gson.toJson(value)
        editor.putString("BankAccountRecent", model)
        editor.apply()
    }

    fun getBankAccountRecent(context: Context): BankAccountRecent? {
        val sharePref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val result = sharePref.getString("BankAccountRecent", null)
        return if (result != null) {
            val gson = Gson()
            val type = object : TypeToken<BankAccountRecent>() {}.type
            gson.fromJson(result, type)
        } else {
            null
        }
    }
}