package pham.hien.barcodescanner

import java.util.*

class BankAccountRequest() {
    var code: String = ""
    var desc: String = ""
    var data: AccountName? = null
    override fun toString(): String {
        return "BankAccountRequest(code='$code', desc='$desc', data='${data?.accountName}')"
    }
}
