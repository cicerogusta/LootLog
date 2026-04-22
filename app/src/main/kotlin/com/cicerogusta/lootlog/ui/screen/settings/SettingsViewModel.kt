package com.cicerogusta.lootlog.ui.screen.settings

import androidx.lifecycle.ViewModel
import com.cicerogusta.lootlog.domain.usecase.LocalizationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val localizationManager: LocalizationManager
) : ViewModel() {

    val currentLanguage: Flow<String> = localizationManager.currentLanguage
    val isLibrasEnabled: Flow<Boolean> = localizationManager.isLibrasEnabled

    val availableLanguages: Flow<List<LocalizationManager.Language>> = kotlinx.coroutines.flow.flow {
        emit(localizationManager.getAvailableLanguages())
    }

    fun setLanguage(languageCode: String) {
        localizationManager.setLanguage(languageCode)
    }

    fun setLibrasEnabled(enabled: Boolean) {
        localizationManager.setLibrasEnabled(enabled)
    }

    fun toggleLibras() {
        localizationManager.toggleLibras()
    }
}
