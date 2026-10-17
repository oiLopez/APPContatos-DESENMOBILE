package thaisa.vitoria.contatos.data

import com.google.gson.JsonParser
import retrofit2.Response

object Repositorio {

    suspend fun listar() = ler { api.listar() }
    suspend fun login(dados: LoginEnvio) = ler { api.login(dados) }
    private val api get() = ApiClient.api

    suspend fun registrar(dados: RegistroEnvio) =
        ler { api.registrar(dados) }

    private suspend fun <T> ler(
        chamada: suspend () -> Response<T>
    ): T {
        val autenticado = Sessao.token != null
        val resposta = chamada()
        if (!resposta.isSuccessful) {
            val texto = resposta.errorBody()?.string()
            val mensagem = runCatching {
                val json = JsonParser.parseString(texto).asJsonObject
                (json["mensagem"] ?: json["erro"])?.asString
            }.getOrNull() ?: "Erro HTTP ${resposta.code()}"
            if (autenticado && resposta.code() in listOf(401, 403)) {
                Sessao.token = null
                error("Sessão encerrada. Faça login novamente.")
            }
            error(mensagem)
        }
        return resposta.body() ?: error("Resposta vazia da API")
    }
}
