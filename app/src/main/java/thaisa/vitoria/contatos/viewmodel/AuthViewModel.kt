package thaisa.vitoria.contatos.viewmodel

import thaisa.vitoria.contatos.data.RegistroEnvio
import thaisa.vitoria.contatos.data.Repositorio

class AuthViewModel : OperacaoViewModel() {
    fun registrar(nome: String, email: String, senha: String) {
        if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
            mensagem = "Preencha nome, email e senha"
            return
        }
        executar {
            val resposta = Repositorio.registrar(
                RegistroEnvio(nome.trim(), email.trim(), senha)
            )
            mensagem = resposta.mensagem ?: "Usuário criado"
        }
    }
}
