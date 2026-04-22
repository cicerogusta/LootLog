package com.cicerogusta.lootlog.ui.screen.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cicerogusta.lootlog.R
import com.cicerogusta.lootlog.data.model.CollectibleItem

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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                        onItemAdded()
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
