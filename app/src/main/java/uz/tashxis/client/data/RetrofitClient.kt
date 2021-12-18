package uz.tashxis.client.data


import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.RetentionManager
import io.paperdb.Paper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.tashxis.client.App
import uz.tashxis.client.business.util.Language

object RetrofitClient {
   /* val chuckerCollector = ChuckerCollector(
        context = App.context!!,
        // Toggles visibility of the push notification
        showNotification = true,
        // Allows to customize the retention period of collected data
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )*/
    private val client = OkHttpClient.Builder().also { client ->
        if (uz.tashxis.client.BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }
    }.build()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val savedToken = Paper.book().read<String>("token")
            var token = ""
            if (savedToken != null) {
                token = savedToken
            }
            val requestBuilder = original.newBuilder()
                .addHeader("token", token)
                .addHeader("Accept-Language", Language.getLanguage())
                .method(original.method, original.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(uz.tashxis.client.business.util.Constants.BASE_URL)
            .client(okHttpClient)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }

}