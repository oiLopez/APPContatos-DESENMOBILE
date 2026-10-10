package thaisa.vitoria.contatos.data

import retrofit2.Response
import retrofit2.http.*

interface Api {
    @POST("usuarios/registrar")
    suspend fun registrar(
        @Body dados: RegistroEnvio
    ): Response<MensagemResposta>
}
