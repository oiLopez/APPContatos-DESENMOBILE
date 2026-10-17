package thaisa.vitoria.contatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread {
            try {
                val conexao = java.net.URL(
                    "http://10.0.2.2:3000/"
                ).openConnection() as java.net.HttpURLConnection

                conexao.connectTimeout = 5000
                conexao.readTimeout = 5000

                android.util.Log.d(
                    "TESTE_REDE",
                    "HTTP: ${conexao.responseCode}"
                )

                conexao.disconnect()
            } catch (e: Exception) {
                android.util.Log.e(
                    "TESTE_REDE",
                    "Erro na conexão direta",
                    e
                )
            }
        }.start()
        setContent {
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    Box(Modifier.fillMaxSize().safeDrawingPadding()) {
                        AppContatos()
                    }
                }
            }
        }
    }
}