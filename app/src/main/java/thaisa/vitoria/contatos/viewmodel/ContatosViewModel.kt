package thaisa.vitoria.contatos.viewmodel

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.runtime.*
import thaisa.vitoria.contatos.data.*

class ContatosViewModel : OperacaoViewModel() {
    var contatos by mutableStateOf<List<Contato>>(emptyList())
        private set

    var nome by mutableStateOf("")
    var email by mutableStateOf("")
    var telefone by mutableStateOf("")
    var endereco by mutableStateOf("")

    var fotoId by mutableStateOf<String?>(null)
        private set

    var fotoLocal by mutableStateOf<Uri?>(null)
        private set

    var pronto by mutableStateOf(false)
        private set

    var concluido by mutableStateOf(false)
        private set

    private var inicializado = false
    private var idAtual: String? = null

    fun listar() = executar {
        contatos = Repositorio.listar()
    }

    fun abrir(id: String?) {
        if (inicializado && idAtual == id) return

        inicializado = true
        idAtual = id

        nome = ""
        email = ""
        telefone = ""
        endereco = ""
        fotoId = null
        fotoLocal = null

        pronto = id == null
        concluido = false
        limparMensagem()

        if (id != null) executar {
            val contato = Repositorio.buscar(id)

            nome = contato.nome
            email = contato.email.orEmpty()
            telefone = contato.telefone.orEmpty()
            endereco = contato.endereco.orEmpty()
            fotoId = contato.fotoId

            pronto = true
        }
    }

    fun salvar(resolver: ContentResolver) {
        if (!pronto) return

        if (nome.isBlank()) {
            mensagem = "Informe o nome do contato"
            return
        }

        executar {
            val uri = fotoLocal

            if (uri != null) {
                fotoId = Repositorio.enviarFoto(resolver, uri)
                fotoLocal = null
            }

            val dados = ContatoEnvio(
                nome.trim(),
                email.trim(),
                telefone,
                endereco,
                fotoId
            )

            val id = idAtual

            if (id == null) {
                Repositorio.criar(dados)
            } else {
                Repositorio.atualizar(id, dados)
            }

            concluido = true
        }
    }

    fun excluir() {
        val id = idAtual ?: return

        executar {
            Repositorio.excluir(id)
            concluido = true
        }
    }

    fun selecionarFoto(uri: Uri?) {
        if (uri != null && !ocupado) {
            fotoLocal = uri
        }
    }

    fun removerFoto() {
        fotoLocal = null
        fotoId = null
    }

    fun fecharEditor() {
        cancelar()
        inicializado = false
        pronto = false
        concluido = false
    }

    fun consumirConclusao() {
        concluido = false
    }

    fun limpar() {
        fecharEditor()
        contatos = emptyList()

        nome = ""
        email = ""
        telefone = ""
        endereco = ""
        fotoId = null
        fotoLocal = null
    }
}