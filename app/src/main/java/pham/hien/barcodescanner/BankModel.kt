package pham.hien.barcodescanner

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BankModel {
    @SerializedName("id")
    @Expose
    var id: String = ""

    @SerializedName("name")
    @Expose
    var name: String = ""

    @SerializedName("code")
    @Expose
    var code: String = ""

    @SerializedName("bin")
    @Expose
    var bin: String = ""

    @SerializedName("shortName")
    @Expose
    var shortName: String = ""

    @SerializedName("logo")
    @Expose
    var logo: String = ""

    @SerializedName("transferSupported")
    @Expose
    var transferSupported: Int = 0

    @SerializedName("lookupSupported")
    @Expose
    var lookupSupported: Int = 1

    @SerializedName("short_name")
    @Expose
    var short_name: String = ""

    @SerializedName("support")
    @Expose
    var support: Int = 3

    @SerializedName("isTransfer")
    @Expose
    var isTransfer: Int = 0

    @SerializedName("swift_code")
    @Expose
    var swift_code: String = ""
    override fun toString(): String {
        return "BankModel(id='$id', name='$name', code='$code', bin='$bin', shortName='$shortName', logo='$logo', transferSupported=$transferSupported, lookupSupported=$lookupSupported, short_name='$short_name', support=$support, isTransfer=$isTransfer, swift_code='$swift_code')\n"
    }


}