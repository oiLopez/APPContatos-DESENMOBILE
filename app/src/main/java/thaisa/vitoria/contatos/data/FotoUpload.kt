package thaisa.vitoria.contatos.data

import android.content.ContentResolver
import android.net.Uri
import java.io.ByteArrayOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

suspend fun prepararFoto(
    resolver: ContentResolver,
    uri: Uri
): MultipartBody.Part = withContext(Dispatchers.IO) {
    val mime = resolver.getType(uri)

    val extensao = when (mime) {
        "image/jpeg" -> "jpg"
        "image/png" -> "png"
        "image/webp" -> "webp"
        else -> error("Selecione uma imagem JPEG, PNG ou WebP")
    }

    val limite = 10 * 1024 * 1024

    val dados = resolver.openInputStream(uri)?.use { entrada ->
        val saida = ByteArrayOutputStream()
        val buffer = ByteArray(8192)
        var total = 0

        while (true) {
            val lidos = entrada.read(buffer)
            if (lidos == -1) break

            total += lidos
            check(total <= limite) {
                "A foto deve ter no máximo 10 MB"
            }

            saida.write(buffer, 0, lidos)
        }

        saida.toByteArray()
    } ?: error("Não foi possível abrir a imagem")

    check(dados.isNotEmpty()) {
        "A imagem está vazia"
    }

    MultipartBody.Part.createFormData(
        "foto",
        "foto.$extensao",
        dados.toRequestBody(requireNotNull(mime).toMediaType())
    )
}