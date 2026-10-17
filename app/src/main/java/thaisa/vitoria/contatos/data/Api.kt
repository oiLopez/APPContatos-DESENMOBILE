package thaisa.vitoria.contatos.data

import retrofit2.Response
import retrofit2.http.*
import thaisa.vitoria.contatos.data.ApiClient.api

interface Api {
    @POST("usuarios/registrar")
    suspend fun registrar(
        @Body dados: RegistroEnvio
    ): Response<MensagemResposta>

    @POST("usuarios/login")
    suspend fun login(@Body dados: LoginEnvio): Response<LoginResposta>

}



