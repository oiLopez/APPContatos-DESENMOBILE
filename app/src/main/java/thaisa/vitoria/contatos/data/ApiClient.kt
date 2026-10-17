
package thaisa.vitoria.contatos.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    const val BASE_URL = "http://127.0.0.1:3000/"

    private val cliente = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val pedido = chain.request().newBuilder()

            Sessao.token?.let {
                pedido.header("Authorization", "Bearer $it")
            }

            chain.proceed(pedido.build())
        }
        .build()

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
