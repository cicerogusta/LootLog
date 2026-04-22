package com.cicerogusta.lootlog.ui.screen.paywall

import androidx.lifecycle.ViewModel
import com.cicerogusta.lootlog.domain.usecase.SubscriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaywallViewModel @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    fun subscribePremium() {
        // Integrar com RevenueCat (futura implementação)
        subscriptionRepository.setPremium(true)
    }

    fun restorePurchases() {
        // Integrar com RevenueCat para restaurar compras
    }
}
