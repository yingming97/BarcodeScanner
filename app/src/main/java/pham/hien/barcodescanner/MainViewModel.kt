package pham.hien.barcodescanner

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val TAG = "YingMing"
    val mListBank = MutableLiveData<ArrayList<BankModel>>()
    val mBankAccountRequest = MutableLiveData<BankAccountRequest>()

    fun getListBank(context: Context) {
        viewModelScope.launch {
            val apiBank = ApiBank.Factory.getInstance(context)
            apiBank?.getListBank()?.enqueue(object : Callback<BankRoot> {
                override fun onResponse(
                    call: Call<BankRoot>?,
                    response: Response<BankRoot>,
                ) {
                    if (response.isSuccessful) {
                        mListBank.value = response.body().data
                    }
                }

                override fun onFailure(call: Call<BankRoot>?, t: Throwable?) {
                    Log.e(TAG, "onFailure: ${t?.message}")
                }

            })
        }

    }

    fun checkBankAccount(context: Context, accountCheck: BankAccountCheck) {
        viewModelScope.launch {
            val apiCheckBankAccount = ApiCheckAccount.Factory.getInstance(context)
            apiCheckBankAccount?.checkAccount(accountCheck)
                ?.enqueue(object : Callback<BankAccountRequest> {
                    override fun onResponse(
                        call: Call<BankAccountRequest>,
                        response: Response<BankAccountRequest>,
                    ) {
                        if (response.isSuccessful) {
                            mBankAccountRequest.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<BankAccountRequest>?, t: Throwable?) {
                        Log.e(TAG, "onFailure: ${t?.message}")
                    }

                })
        }
    }
}