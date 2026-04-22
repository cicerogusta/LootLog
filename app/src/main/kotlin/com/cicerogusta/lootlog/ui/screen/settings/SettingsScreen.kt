package com.cicerogusta.lootlog.ui.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cicerogusta.lootlog.R
import com.cicerogusta.lootlog.ui.components.LibrasAccessibilityButton

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val currentLanguage by viewModel.currentLanguage.collectAsState(initial = "pt")
    val isLibrasEnabled by viewModel.isLibrasEnabled.collectAsState(initial = false)
    val availableLanguages by viewModel.availableLanguages.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Language Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    stringResource(R.string.accessibility),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // LIBRAS Toggle
                LibrasAccessibilityButton(
                    isLibrasEnabled = isLibrasEnabled,
                    onToggle = { viewModel.setLibrasEnabled(it) },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Language Selection
                Text(
                    "Idioma",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        availableLanguages.forEachIndexed { index, language ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.setLanguage(language.code) }
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    language.flag,
                                    fontSize = 24.sp
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        language.name,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                RadioButton(
                                    selected = currentLanguage == language.code,
                                    onClick = { viewModel.setLanguage(language.code) }
                                )
                            }

                            if (index < availableLanguages.size - 1) {
                                Divider(modifier = Modifier.padding(horizontal = 12.dp))
                            }
                        }
                    }
                }

                // Info Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    Text(
                        "Sobre Acessibilidade",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        "LootLog foi desenvolvido com foco em acessibilidade para todos.\n\n" +
                        "• LIBRAS: Interpretações em Língua Brasileira de Sinais\n" +
                        "• Múltiplos Idiomas: Português, Inglês, Espanhol, Francês, Alemão\n" +
                        "• Compatibilidade: TalkBack e outras ferramentas de acessibilidade\n\n" +
                        "Para mais informações, visite nosso site de acessibilidade.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
