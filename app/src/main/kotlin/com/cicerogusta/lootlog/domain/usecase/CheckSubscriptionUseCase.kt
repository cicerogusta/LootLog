package com.cicerogusta.lootlog.domain.usecase

import com.cicerogusta.lootlog.data.repository.CollectibleItemRepository
import com.cicerogusta.lootlog.domain.model.SubscriptionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CheckSubscriptionUseCase @Inject constructor(
    private val itemRepository: CollectibleItemRepository,
    private val subscriptionRepository: SubscriptionRepository
) {
    operator fun invoke(): Flow<SubscriptionState> {
        return itemRepository.getItemCount().map { itemCount ->
            SubscriptionState(
                isPremium = subscriptionRepository.isPremium(),
                itemCount = itemCount,
                canAddMoreItems = subscriptionRepository.isPremium() || itemCount < 15
            )
        }
    }
}
