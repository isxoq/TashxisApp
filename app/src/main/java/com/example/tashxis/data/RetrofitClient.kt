package com.example.tashxis.data

import com.example.tashxis.BuildConfig
import com.example.tashxis.business.util.Constants
import com.example.tashxis.business.util.Language
import io.paperdb.Paper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val client = OkHttpClient.Builder().also { client ->
        if (BuildConfig.DEBUG) {
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
            val requset = requestBuilder.build()
            chain.proceed(requset)
        }.build()
    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }

}