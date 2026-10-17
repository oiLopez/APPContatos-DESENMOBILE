package thaisa.vitoria.contatos.data

data class RegistroEnvio(
    val nome: String,
    val email: String,
    val senha: String
)

data class MensagemResposta(val mensagem: String?)

data class LoginEnvio(val email: String, val senha: String)
data class LoginResposta(val token: String?)
