package thaisa.vitoria.contatos

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import thaisa.vitoria.contatos.data.Sessao
import thaisa.vitoria.contatos.ui.CadastroUsuarioTela
import thaisa.vitoria.contatos.ui.Formulario
import thaisa.vitoria.contatos.ui.LoginTela
import thaisa.vitoria.contatos.viewmodel.AuthViewModel

@Composable
fun AppContatos() {
    val nav = rememberNavController()
    val auth: AuthViewModel = viewModel()

    LaunchedEffect(Sessao.token) {
        val rota = nav.currentDestination?.route
        if (Sessao.token == null && rota == "lista") {
            nav.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }
    NavHost(nav, startDestination = "login") {
        composable("login") {
            LoginTela(auth,
                cadastrar = { nav.navigate("registrar") },
                entrar = {
                    nav.navigate("lista") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("registrar") {
            CadastroUsuarioTela(auth, voltar = { nav.popBackStack() })
        }
        composable("lista") {
            Formulario("Sessão iniciada", false, null) {
                Text("Login realizado com sucesso")
                Button(onClick = { auth.sair() }) { Text("Sair") }
            }
        }
    }
}
