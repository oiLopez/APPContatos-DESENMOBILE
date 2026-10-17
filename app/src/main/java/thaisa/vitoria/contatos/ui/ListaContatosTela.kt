package thaisa.vitoria.contatos.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import thaisa.vitoria.contatos.viewmodel.ContatosViewModel


@Composable
fun ListaContatosTela(
    vm: ContatosViewModel,
    sair: () -> Unit,
    novo: (() -> Unit)? = null,
    abrir: ((String) -> Unit)? = null
) {
    LaunchedEffect(Unit) { vm.listar() }
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Contatos", style = MaterialTheme.typography.titleLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            novo?.let { acao ->
                Button(onClick = acao, enabled = !vm.ocupado) {
                    Text("Novo")
                }
            }
            Button(onClick = { vm.listar() }, enabled = !vm.ocupado) {
                Text("Atualizar")
            }
            TextButton(onClick = sair, enabled = !vm.ocupado) {
                Text("Sair")
            }
        }
        if (vm.ocupado) LinearProgressIndicator(Modifier.fillMaxWidth())
        vm.mensagem?.let { Text(it) }
        if (!vm.ocupado && vm.contatos.isEmpty()) {
            Text("Nenhum contato encontrado")
        }
        LazyColumn(Modifier.weight(1f)) {
            items(vm.contatos, key = { it.id }) { contato ->
                Column(
                    Modifier.fillMaxWidth().clickable(
                        enabled = !vm.ocupado && abrir != null,
                        onClick = { abrir?.invoke(contato.id) }
                    ).padding(vertical = 8.dp)
                ) {
                    Text(contato.nome)
                    Text(contato.telefone.orEmpty())
                    HorizontalDivider()
                }
            }
        }
    }
}
