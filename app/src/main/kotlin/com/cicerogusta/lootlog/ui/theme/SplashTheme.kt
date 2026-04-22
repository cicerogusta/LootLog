package com.cicerogusta.lootlog.ui.theme

import androidx.compose.ui.graphics.Color

// Dark Mode Splash Theme Colors
object SplashColors {
    val DarkBackground = Color(0xFF0A0E27)
    val DarkCardBg = Color(0xFF1A1F3A)
    val NeonBlue = Color(0xFF00D9FF)
    val NeonBlueDim = Color(0xFF00D9FF).copy(alpha = 0.3f)
    val GlowBlue = Color(0xFF0088FF)
    val GlowBlueDim = Color(0xFF0088FF).copy(alpha = 0.2f)
    val SurfaceBlue = Color(0xFF0F1535)
    val AccentPurple = Color(0xFF6A5ACD)
}

// Paleta estendida para uso em todo o app
object NeonPalette {
    // Primary Colors
    val PrimaryNeon = Color(0xFF00D9FF)
    val PrimaryGlow = Color(0xFF0088FF)

    // Background
    val BackgroundDark = Color(0xFF0A0E27)
    val SurfaceDark = Color(0xFF1A1F3A)
    val SurfaceVariant = Color(0xFF0F1535)

    // Secondary
    val SecondaryNeon = Color(0xFF00FF88)
    val SecondaryGlow = Color(0xFF00CC66)

    // Accent
    val AccentCyan = Color(0xFF00FFFF)
    val AccentPurple = Color(0xFF6A5ACD)

    // Functional
    val Success = Color(0xFF00FF88)
    val Warning = Color(0xFFFFAA00)
    val Error = Color(0xFFFF0055)

    // Neutral
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFF00D9FF)
    val TextTertiary = Color(0xFF888899)
}
