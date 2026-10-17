package thaisa.vitoria.contatos.viewmodel

import androidx.compose.runtime.*
import thaisa.vitoria.contatos.data.LoginEnvio // Certifique-se de importar o LoginEnvio se necessário
import thaisa.vitoria.contatos.data.RegistroEnvio
import thaisa.vitoria.contatos.data.Repositorio
import thaisa.vitoria.contatos.data.Sessao // Importe sua classe de Sessao conforme seu projeto

class AuthViewModel : OperacaoViewModel() {

    // 1. Variável de estado para controlar se o login deu certo
    var entrou by mutableStateOf(false)
        private set

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

    fun login(email: String, senha: String) {
        if (email.isBlank() || senha.isBlank()) {
            mensagem = "Preencha email e senha"
            return
        }
        executar {
            val resposta = Repositorio.login(LoginEnvio(email.trim(), senha))
            val token = resposta.token?.takeIf { it.isNotBlank() }
                ?: error("A API não retornou um token válido")
            Sessao.token = token
            entrou = true
        }
    }

    fun consumirEntrada() {
        entrou = false
    }

    fun sair() {
        cancelar()
        entrou = false
        Sessao.token = null
    }
}