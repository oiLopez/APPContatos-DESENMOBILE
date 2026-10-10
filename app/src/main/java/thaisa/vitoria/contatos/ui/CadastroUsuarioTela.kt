package thaisa.vitoria.contatos.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import thaisa.vitoria.contatos.viewmodel.AuthViewModel

@Composable
fun CadastroUsuarioTela(
    vm: AuthViewModel,
    voltar: (() -> Unit)? = null
) {
    var nome by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    LaunchedEffect(Unit) { vm.limparMensagem() }
    Formulario("Criar usuário", vm.ocupado, vm.mensagem) {
        Campo("Nome", nome, { nome = it }, !vm.ocupado)
        Campo("Email", email, { email = it }, !vm.ocupado)
        Campo("Senha", senha, { senha = it }, !vm.ocupado, true)
        Button(
            onClick = { vm.registrar(nome, email, senha) },
            enabled = !vm.ocupado
        ) { Text("Cadastrar") }
        voltar?.let { acao ->
            TextButton(onClick = acao, enabled = !vm.ocupado) {
                Text("Voltar ao login")
            }
        }
    }
}
