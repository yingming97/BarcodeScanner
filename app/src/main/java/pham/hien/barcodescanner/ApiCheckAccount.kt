package pham.hien.barcodescanner

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiCheckAccount {

    @Headers("Content-Type:application/json",
        "x-client-id:eb19ea19-a7b3-4ca0-8073-4a1ba3b53935",
        "x-api-key:99715fad-7b5b-4845-8dd6-9b08e712e6ba")
    @POST("lookup")
     fun checkAccount(@Body accountCheck: BankAccountCheck): Call<BankAccountRequest>

    object Factory {
        private var service: ApiCheckAccount? = null
        fun getInstance(context: Context): ApiCheckAccount? {
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
                    ApiCheckAccount::class.java)
            }
            return service
        }
    }
}