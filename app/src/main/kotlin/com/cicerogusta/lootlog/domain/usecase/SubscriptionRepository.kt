package com.cicerogusta.lootlog.domain.usecase

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscriptionRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val _isPremium = MutableStateFlow(loadPremiumStatus())

    val isPremiumFlow: Flow<Boolean> = _isPremium.asStateFlow()

    fun isPremium(): Boolean = sharedPreferences.getBoolean(KEY_IS_PREMIUM, false)

    fun setPremium(premium: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_PREMIUM, premium).apply()
        _isPremium.value = premium
    }

    private fun loadPremiumStatus(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_PREMIUM, false)
    }

    companion object {
        private const val KEY_IS_PREMIUM = "is_premium"
    }
}
