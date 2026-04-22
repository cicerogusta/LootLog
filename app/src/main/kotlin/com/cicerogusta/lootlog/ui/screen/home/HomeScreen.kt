package com.cicerogusta.lootlog.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cicerogusta.lootlog.R
import com.cicerogusta.lootlog.data.model.CollectibleItem
import com.cicerogusta.lootlog.ui.components.CollectibleItemCard

@Composable
fun HomeScreen(
    onNavigateToAddItem: () -> Unit,
    onNavigateToPaywall: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val items by viewModel.items.collectAsState(initial = emptyList())
    val subscriptionState by viewModel.subscriptionState.collectAsState(
        initial = com.cicerogusta.lootlog.domain.model.SubscriptionState()
    )
    val navigateToPaywall by viewModel.navigateToPaywall.collectAsState()
    var showLimitDialog by remember { mutableStateOf(false) }

    LaunchedEffect(navigateToPaywall) {
        if (navigateToPaywall) {
            showLimitDialog = true
            viewModel.resetPaywallNavigation()
        }
    }

    if (showLimitDialog) {
        LimitReachedDialog(
            currentCount = subscriptionState.itemCount,
            onUpgradeClick = {
                showLimitDialog = false
                onNavigateToPaywall()
            },
            onDismiss = {
                showLimitDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home_title)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (subscriptionState.canAddMoreItems) {
                        onNavigateToAddItem()
                    } else {
                        viewModel.onLimitReached()
                    }
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_item))
            }
        }
    ) { innerPadding ->
        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(stringResource(R.string.empty_collection))
                    Text(
                        stringResource(R.string.start_collecting),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->
                    CollectibleItemCard(
                        item = item,
                        onDelete = { viewModel.deleteItem(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun LimitReachedDialog(
    currentCount: Int,
    onUpgradeClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(R.string.limit_reached),
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Você atingiu o limite de 15 itens na versão gratuita.")
                Text("Upgrade para Premium e desbloqueie:")
                Text("✓ Itens ilimitados")
                Text("✓ Sem anúncios")
                Text("✓ Backup na nuvem")
            }
        },
        confirmButton = {
            TextButton(onClick = onUpgradeClick) {
                Text(
                    stringResource(R.string.upgrade_premium),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Mais tarde")
            }
        }
    )
}
