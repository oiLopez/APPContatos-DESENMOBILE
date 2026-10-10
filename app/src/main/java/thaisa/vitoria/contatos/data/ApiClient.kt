package thaisa.vitoria.contatos.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BASE_URL = "http://10.0.2.2:3000/"

    private val cliente = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val pedido = chain.request().newBuilder()
            Sessao.token?.let {
                pedido.header("Authorization", "Bearer $it")
            }
            chain.proceed(pedido.build())
        }.build()

    val api: Api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(cliente)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().serializeNulls().create()
            )
        )
        .build()
        .create(Api::class.java)
}

