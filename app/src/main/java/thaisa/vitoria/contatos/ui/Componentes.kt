package thaisa.vitoria.contatos.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp

@Composable
fun Formulario(
    titulo: String,
    ocupado: Boolean,
    mensagem: String?,
    conteudo: @Composable ColumnScope.() -> Unit
) {
    Column(
        Modifier.fillMaxSize().imePadding()
            .verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(titulo, style = MaterialTheme.typography.titleLarge)
        if (ocupado) LinearProgressIndicator(Modifier.fillMaxWidth())
        mensagem?.let { Text(it) }
        conteudo()
    }
}

@Composable
fun Campo(
    rotulo: String,
    valor: String,
    mudar: (String) -> Unit,
    habilitado: Boolean = true,
    senha: Boolean = false
) {
    OutlinedTextField(
        value = valor, onValueChange = mudar,
        label = { Text(rotulo) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true, enabled = habilitado,
        visualTransformation = if (senha) PasswordVisualTransformation()
        else VisualTransformation.None
    )
}
