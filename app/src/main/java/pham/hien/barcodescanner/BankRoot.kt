package pham.hien.barcodescanner

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BankRoot {
    @SerializedName("code")
    @Expose
    var code: String = ""

    @SerializedName("desc")
    @Expose
    var desc: String = ""

    @SerializedName("data")
    @Expose
    var data: ArrayList<BankModel> = ArrayList()
    override fun toString(): String {
        return "BankRoot(code='$code', desc='$desc', data='$data')"
    }
}