package com.cicerogusta.lootlog.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cicerogusta.lootlog.data.model.CollectibleItem
import com.cicerogusta.lootlog.data.repository.CollectibleItemRepository
import com.cicerogusta.lootlog.domain.model.SubscriptionState
import com.cicerogusta.lootlog.domain.usecase.CheckSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepository: CollectibleItemRepository,
    private val checkSubscriptionUseCase: CheckSubscriptionUseCase
) : ViewModel() {

    val items: Flow<List<CollectibleItem>> = itemRepository.getAllItems()
    val subscriptionState: Flow<SubscriptionState> = checkSubscriptionUseCase()

    private val _navigateToPaywall = MutableStateFlow(false)
    val navigateToPaywall: StateFlow<Boolean> = _navigateToPaywall.asStateFlow()

    fun onLimitReached() {
        _navigateToPaywall.value = true
    }

    fun resetPaywallNavigation() {
        _navigateToPaywall.value = false
    }

    fun insertItem(item: CollectibleItem) {
        viewModelScope.launch {
            itemRepository.insertItem(item)
        }
    }

    fun deleteItem(item: CollectibleItem) {
        viewModelScope.launch {
            itemRepository.deleteItem(item)
        }
    }
}
