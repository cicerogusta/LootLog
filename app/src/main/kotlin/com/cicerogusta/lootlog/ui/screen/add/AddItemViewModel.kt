package com.cicerogusta.lootlog.ui.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cicerogusta.lootlog.data.model.CollectibleItem
import com.cicerogusta.lootlog.data.repository.CollectibleItemRepository
import com.cicerogusta.lootlog.domain.usecase.SubscriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val itemRepository: CollectibleItemRepository,
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    val itemCount: Flow<Int> = itemRepository.getItemCount()
    val isPremium: Boolean
        get() = subscriptionRepository.isPremium()

    fun addItem(item: CollectibleItem) {
        viewModelScope.launch {
            itemRepository.insertItem(item)
        }
    }
}
