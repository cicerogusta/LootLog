package com.cicerogusta.lootlog.ui.screen.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cicerogusta.lootlog.R
import com.cicerogusta.lootlog.data.model.CollectibleItem
import kotlinx.coroutines.launch

@Composable
fun AddItemScreen(
    onBackClick: () -> Unit,
    onItemAdded: () -> Unit,
    viewModel: AddItemViewModel = hiltViewModel()
) {
    var itemName by remember { mutableStateOf("") }
    var rarity by remember { mutableStateOf("") }
    var pricePaid by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val itemCount by viewModel.itemCount.collectAsState(initial = 0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Item") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (!viewModel.isPremium && itemCount >= 14) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Filled.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Column {
                            Text(
                                "Limite se aproximando",
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            Text(
                                "Você tem ${15 - itemCount} item(ns) gratuito(s) restante(s)",
                                fontSize = androidx.compose.ui.unit.sp(12f)
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("Nome do Item") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = rarity,
                onValueChange = { rarity = it },
                label = { Text("Raridade") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ex: Comum, Raro, Épico, Lendário") }
            )

            OutlinedTextField(
                value = pricePaid,
                onValueChange = { pricePaid = it },
                label = { Text("Preço Pago (R\$)") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ex: 199.90") }
            )

            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("URL da Imagem (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("https://...") }
            )

            Button(
                onClick = {
                    if (itemName.isNotBlank() && rarity.isNotBlank() && pricePaid.isNotBlank()) {
                        val price = pricePaid.toDoubleOrNull() ?: 0.0
                        val item = CollectibleItem(
                            name = itemName,
                            rarity = rarity,
                            pricePaid = price,
                            imageUrl = imageUrl.takeIf { it.isNotBlank() }
                        )
                        viewModel.addItem(item)

                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                "Item adicionado com sucesso!"
                            )
                        }

                        onItemAdded()
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                "Preencha todos os campos obrigatórios"
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(stringResource(R.string.add_item))
            }
        }
    }
}
