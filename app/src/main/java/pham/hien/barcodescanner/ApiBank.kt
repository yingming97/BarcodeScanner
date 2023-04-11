package pham.hien.barcodescanner

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiBank {

    @GET("banks")
     fun getListBank(): Call<BankRoot>

    object Factory {
        private var service: ApiBank? = null
        fun getInstance(context: Context): ApiBank? {
            val baseUrl = "https://api.vietqr.io/v2/"

            if (service == null) {
                val builder = OkHttpClient().newBuilder()
                builder.readTimeout(15, TimeUnit.SECONDS)
                builder.connectTimeout(10, TimeUnit.SECONDS)
                builder.writeTimeout(10, TimeUnit.SECONDS)
                val cacheSize = 50 * 1024 * 1024 // 50 MiB
                val cache = Cache(context.cacheDir, cacheSize.toLong())
                builder.cache(cache)
                val retrofit = Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
                service = retrofit.create(
                    ApiBank::class.java)
            }
            return service
        }
    }
}