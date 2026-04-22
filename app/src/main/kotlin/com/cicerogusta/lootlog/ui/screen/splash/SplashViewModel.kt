package com.cicerogusta.lootlog.ui.screen.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import com.cicerogusta.lootlog.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val isLoggedIn: Flow<Boolean> = authRepository.isLoggedIn
}
