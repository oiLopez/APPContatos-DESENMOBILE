package thaisa.vitoria.contatos.data

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @POST("usuarios/registrar")
    suspend fun registrar(
        @Body dados: RegistroEnvio
    ): Response<MensagemResposta>

    @POST("usuarios/login")
    suspend fun login(
        @Body dados: LoginEnvio
    ): Response<LoginResposta>

    @GET("contatos")
    suspend fun listar(): Response<List<Contato>>

    @POST("contatos")
    suspend fun criar(
        @Body dados: ContatoEnvio
    ): Response<ContatoCriadoResposta>

    @GET("contatos/{id}")
    suspend fun buscar(
        @Path("id") id: String
    ): Response<Contato>

    @PUT("contatos/{id}")
    suspend fun atualizar(
        @Path("id") id: String,
        @Body dados: ContatoEnvio
    ): Response<Contato>

    @DELETE("contatos/{id}")
    suspend fun excluir(
        @Path("id") id: String
    ): Response<MensagemResposta>

    @Multipart
    @POST("upload")
    suspend fun enviarFoto(
        @Part foto: MultipartBody.Part
    ): Response<UploadResposta>
}