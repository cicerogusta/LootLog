package com.cicerogusta.lootlog.ui.components

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn

@Composable
fun GoogleSignInButton(
    onClick: (String) -> Unit,
    enabled: Boolean = true
) {
    val context = LocalContext.current

    TextButton(
        onClick = {
            signInWithGoogle(context, onClick)
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(vertical = 8.dp),
        enabled = enabled
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Entrar com Google",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

private fun signInWithGoogle(
    context: Context,
    onIdToken: (String) -> Unit
) {
    try {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        account?.idToken?.let { onIdToken(it) }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
