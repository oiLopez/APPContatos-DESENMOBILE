package thaisa.vitoria.contatos.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.io.IOException
import kotlinx.coroutines.*

open class OperacaoViewModel : ViewModel() {
    var ocupado by mutableStateOf(false)
        private set
    var mensagem by mutableStateOf<String?>(null)
        protected set
    private var trabalho: Job? = null

    protected fun executar(acao: suspend () -> Unit) {
        if (ocupado) return
        ocupado = true
        mensagem = null
        trabalho = viewModelScope.launch {
            try {
                acao()
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                android.util.Log.e(
                    "API_CONTATOS",
                    "Falha na comunicação com o servidor",
                    e
                )
                mensagem = "${e.javaClass.simpleName}: ${e.message}"
            } catch (e: Exception) {
                mensagem = e.message ?: "Não foi possível concluir"
            } finally {
                ocupado = false
            }
        }
    }

    fun limparMensagem() { mensagem = null }

    fun cancelar() {
        trabalho?.cancel()
        ocupado = false
        mensagem = null
    }
}
