package com.cicerogusta.lootlog.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cicerogusta.lootlog.data.repository.AuthRepository
import com.cicerogusta.lootlog.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val isLoggedIn: Flow<Boolean> = authRepository.isLoggedIn
    val currentUser: Flow<User?> = authRepository.currentUser

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun signUp(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null

            val result = authRepository.signUp(email, password)
            result
                .onSuccess {
                    _authError.value = null
                }
                .onFailure { error ->
                    _authError.value = error.message ?: "Erro ao criar conta"
                }

            _isLoading.value = false
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null

            val result = authRepository.signIn(email, password)
            result
                .onSuccess {
                    _authError.value = null
                }
                .onFailure { error ->
                    _authError.value = error.message ?: "Erro ao fazer login"
                }

            _isLoading.value = false
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null

            val result = authRepository.signInWithGoogle(idToken)
            result
                .onSuccess {
                    _authError.value = null
                }
                .onFailure { error ->
                    _authError.value = error.message ?: "Erro ao fazer login com Google"
                }

            _isLoading.value = false
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }

    fun clearError() {
        _authError.value = null
    }
}
