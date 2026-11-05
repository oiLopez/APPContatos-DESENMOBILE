package thaisa.vitoria.contatos

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.*
import thaisa.vitoria.contatos.data.Sessao
import thaisa.vitoria.contatos.ui.*
import thaisa.vitoria.contatos.viewmodel.*

@Composable
fun AppContatos() {
    val nav = rememberNavController()
    val auth: AuthViewModel = viewModel()
    val contatos: ContatosViewModel = viewModel()

    LaunchedEffect(Sessao.token) {
        val rota = nav.currentDestination?.route

        if (
            Sessao.token == null &&
            rota in listOf("lista", "novo", "contato/{id}")
        ) {
            contatos.limpar()

            nav.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(nav, startDestination = "login") {
        composable("login") {
            LoginTela(
                auth,
                cadastrar = {
                    nav.navigate("registrar")
                },
                entrar = {
                    nav.navigate("lista") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("registrar") {
            CadastroUsuarioTela(
                auth,
                voltar = { nav.popBackStack() }
            )
        }

        composable("lista") {
            ListaContatosTela(
                contatos,
                sair = {
                    contatos.limpar()
                    auth.sair()
                },
                novo = {
                    nav.navigate("novo")
                },
                abrir = { id ->
                    nav.navigate("contato/$id")
                }
            )
        }

        composable("novo") {
            ContatoTela(
                contatos,
                id = null,
                voltar = { nav.popBackStack() }
            )
        }

        composable(
            "contato/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { entrada ->
            val id = requireNotNull(
                entrada.arguments?.getString("id")
            )

            ContatoTela(
                contatos,
                id = id,
                voltar = { nav.popBackStack() }
            )
        }
    }
}