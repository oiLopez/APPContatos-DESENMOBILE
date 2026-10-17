package thaisa.vitoria.contatos.viewmodel

import androidx.compose.runtime.*
import thaisa.vitoria.contatos.data.Contato
import thaisa.vitoria.contatos.data.Repositorio

class ContatosViewModel : OperacaoViewModel() {
    var contatos by mutableStateOf<List<Contato>>(emptyList())
        private set

    fun listar() = executar {
        contatos = Repositorio.listar()
    }

    fun limpar() {
        cancelar()
        contatos = emptyList()
    }
}
