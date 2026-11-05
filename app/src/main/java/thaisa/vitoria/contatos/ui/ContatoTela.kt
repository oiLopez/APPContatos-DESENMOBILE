package thaisa.vitoria.contatos.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import thaisa.vitoria.contatos.data.ApiClient
import thaisa.vitoria.contatos.viewmodel.ContatosViewModel

@Composable
fun ContatoTela(
    vm: ContatosViewModel,
    id: String?,
    voltar: () -> Unit
) {
    var confirmar by remember { mutableStateOf(false) }

    val resolver = LocalContext.current.contentResolver

    val seletor = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        vm.selecionarFoto(uri)
    }

    LaunchedEffect(id) {
        vm.abrir(id)
    }

    LaunchedEffect(vm.concluido) {
        if (vm.concluido) {
            vm.consumirConclusao()
            vm.fecharEditor()
            voltar()
        }
    }

    val sair = {
        vm.fecharEditor()
        voltar()
    }

    BackHandler {
        if (!vm.ocupado) sair()
    }

    Formulario(
        if (id == null) "Novo contato" else "Editar contato",
        vm.ocupado,
        vm.mensagem
    ) {
        if (!vm.pronto && !vm.ocupado) {
            Button(onClick = {
                vm.fecharEditor()
                vm.abrir(id)
            }) {
                Text("Tentar novamente")
            }
        }

        val habilitado = vm.pronto && !vm.ocupado

        Campo("Nome", vm.nome, { vm.nome = it }, habilitado)
        Campo("Email", vm.email, { vm.email = it }, habilitado)
        Campo("Telefone", vm.telefone, { vm.telefone = it }, habilitado)
        Campo("Endereço", vm.endereco, { vm.endereco = it }, habilitado)

        val imagem = vm.fotoLocal
            ?: vm.fotoId?.let { "${ApiClient.BASE_URL}upload/$it" }

        if (imagem != null) {
            AsyncImage(
                model = imagem,
                contentDescription = "Foto do contato",
                modifier = Modifier.size(120.dp)
            )
        }

        Button(
            onClick = {
                seletor.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
            enabled = habilitado
        ) {
            Text("Escolher foto")
        }

        if (imagem != null) {
            TextButton(
                onClick = { vm.removerFoto() },
                enabled = habilitado
            ) {
                Text("Remover foto")
            }
        }

        Button(
            onClick = { vm.salvar(resolver) },
            enabled = habilitado
        ) {
            Text("Salvar")
        }

        if (id != null) {
            TextButton(
                onClick = { confirmar = true },
                enabled = habilitado
            ) {
                Text("Excluir")
            }
        }

        TextButton(
            onClick = sair,
            enabled = !vm.ocupado
        ) {
            Text("Voltar")
        }
    }

    if (confirmar) {
        AlertDialog(
            onDismissRequest = { confirmar = false },
            title = { Text("Excluir contato") },
            text = { Text("Deseja excluir este contato?") },
            confirmButton = {
                TextButton(onClick = {
                    confirmar = false
                    vm.excluir()
                }) {
                    Text("Excluir")
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}