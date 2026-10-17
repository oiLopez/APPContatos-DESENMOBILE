package thaisa.vitoria.contatos.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import thaisa.vitoria.contatos.viewmodel.AuthViewModel

@Composable
fun LoginTela(
    vm: AuthViewModel,
    cadastrar: () -> Unit,
    entrar: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    LaunchedEffect(Unit) { vm.limparMensagem() }
    LaunchedEffect(vm.entrou) {
        if (vm.entrou) {
            vm.consumirEntrada()
            entrar()
        }
    }
    Formulario("Login", vm.ocupado, vm.mensagem) {
        Campo("Email", email, { email = it }, !vm.ocupado)
        Campo("Senha", senha, { senha = it }, !vm.ocupado, true)
        Button(
            onClick = { vm.login(email, senha) },
            enabled = !vm.ocupado
        ) { Text("Entrar") }
        TextButton(onClick = cadastrar, enabled = !vm.ocupado) {
            Text("Criar usuário")
        }
    }
}
