package thaisa.vitoria.contatos.data

import com.google.gson.annotations.SerializedName
data class RegistroEnvio(
    val nome: String,
    val email: String,
    val senha: String
)

data class MensagemResposta(val mensagem: String?)

data class LoginEnvio(val email: String, val senha: String)
data class LoginResposta(val token: String?)

data class Contato(
    @field:SerializedName("_id") val id: String,
    val nome: String,
    val email: String? = null,
    val telefone: String? = null,
    val endereco: String? = null,
    val fotoId: String? = null
)
