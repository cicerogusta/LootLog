package com.cicerogusta.lootlog.domain.usecase

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalizationManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val context: Context
) {
    private val _currentLanguage = MutableStateFlow(loadLanguage())
    val currentLanguage: Flow<String> = _currentLanguage.asStateFlow()

    private val _isLibrasEnabled = MutableStateFlow(loadLibrasPreference())
    val isLibrasEnabled: Flow<Boolean> = _isLibrasEnabled.asStateFlow()

    fun setLanguage(languageCode: String) {
        sharedPreferences.edit().putString(KEY_LANGUAGE, languageCode).apply()
        _currentLanguage.value = languageCode
        applyLanguageChange(languageCode)
    }

    fun setLibrasEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_LIBRAS_ENABLED, enabled).apply()
        _isLibrasEnabled.value = enabled
    }

    private fun loadLanguage(): String {
        return sharedPreferences.getString(
            KEY_LANGUAGE,
            Locale.getDefault().language
        ) ?: "pt"
    }

    private fun loadLibrasPreference(): Boolean {
        return sharedPreferences.getBoolean(KEY_LIBRAS_ENABLED, false)
    }

    private fun applyLanguageChange(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun getAvailableLanguages(): List<Language> {
        return listOf(
            Language("pt", "Português (Brasil)", "🇧🇷"),
            Language("en", "English", "🇺🇸"),
            Language("es", "Español", "🇪🇸"),
            Language("fr", "Français", "🇫🇷"),
            Language("de", "Deutsch", "🇩🇪")
        )
    }

    data class Language(
        val code: String,
        val name: String,
        val flag: String
    )

    companion object {
        private const val KEY_LANGUAGE = "selected_language"
        private const val KEY_LIBRAS_ENABLED = "libras_enabled"
    }
}
