package com.cicerogusta.lootlog.ui.screen.splash

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Cores Dark Mode + Neon Blue
private val DarkBackground = Color(0xFF0A0E27)
private val NeonBlue = Color(0xFF00D9FF)
private val NeonBlueDim = Color(0xFF00D9FF).copy(alpha = 0.3f)
private val DarkCardBg = Color(0xFF1A1F3A)
private val GlowBlue = Color(0xFF0088FF)

@Composable
fun SplashScreen(
    onNavigateNext: () -> Unit
) {
    var showContent by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(4000) // Mostrar splash por 4 segundos
        showContent = false
        delay(500)
        onNavigateNext()
    }

    if (showContent) {
        SplashContent()
    }
}

@Composable
fun SplashContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DarkBackground,
                        Color(0xFF0F1535),
                        DarkBackground
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF1A1F3A).copy(alpha = 0.3f),
                            DarkBackground,
                            DarkBackground
                        ),
                        radius = 800f
                    )
                )
        ) {
            // Espaço superior
            Box(modifier = Modifier.weight(1f))

            // Diamante animado com scanner
            DiamondScanner()

            // Espaço do meio
            Box(modifier = Modifier.weight(0.3f))

            // Texto
            SplashTextSection()

            // Espaço inferior
            Box(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun DiamondScanner() {
    val infiniteTransition = rememberInfiniteTransition(label = "diamond_transition")

    // Rotação do diamante
    val diamondRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "diamond_rotation"
    )

    // Escaneamento horizontal (scanner line)
    val scannerOffset by infiniteTransition.animateFloat(
        initialValue = -150f,
        targetValue = 150f,
        animationSpec = infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Restart
        ),
        label = "scanner_offset"
    )

    // Glow pulsante
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(1500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    // Fade in/out do scanner
    val scannerAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scanner_alpha"
    )

    Box(
        modifier = Modifier
            .size(250.dp)
            .drawBehind {
                // Glow externo (aura)
                drawCircle(
                    color = NeonBlue.copy(alpha = glowAlpha * 0.4f),
                    radius = 150.dp.toPx(),
                    style = Stroke(width = 40.dp.toPx())
                )
                // Segundo glow
                drawCircle(
                    color = GlowBlue.copy(alpha = glowAlpha * 0.2f),
                    radius = 170.dp.toPx(),
                    style = Stroke(width = 60.dp.toPx())
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Diamond shape animado
        Box(
            modifier = Modifier
                .size(180.dp)
                .rotate(diamondRotation)
                .drawBehind {
                    // Glow interno
                    drawCircle(
                        color = NeonBlue.copy(alpha = glowAlpha * 0.6f),
                        radius = 95.dp.toPx()
                    )

                    // Diamante (outline)
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val size = 80.dp.toPx()

                    // Top point
                    val topX = centerX
                    val topY = centerY - size

                    // Right point
                    val rightX = centerX + size
                    val rightY = centerY

                    // Bottom point
                    val bottomX = centerX
                    val bottomY = centerY + size

                    // Left point
                    val leftX = centerX - size
                    val leftY = centerY

                    // Desenhar linhas do diamante
                    drawLine(
                        color = NeonBlue,
                        start = androidx.compose.ui.geometry.Offset(topX, topY),
                        end = androidx.compose.ui.geometry.Offset(rightX, rightY),
                        strokeWidth = 3.dp.toPx()
                    )
                    drawLine(
                        color = NeonBlue,
                        start = androidx.compose.ui.geometry.Offset(rightX, rightY),
                        end = androidx.compose.ui.geometry.Offset(bottomX, bottomY),
                        strokeWidth = 3.dp.toPx()
                    )
                    drawLine(
                        color = NeonBlue,
                        start = androidx.compose.ui.geometry.Offset(bottomX, bottomY),
                        end = androidx.compose.ui.geometry.Offset(leftX, leftY),
                        strokeWidth = 3.dp.toPx()
                    )
                    drawLine(
                        color = NeonBlue,
                        start = androidx.compose.ui.geometry.Offset(leftX, leftY),
                        end = androidx.compose.ui.geometry.Offset(topX, topY),
                        strokeWidth = 3.dp.toPx()
                    )

                    // Linhas internas (facetas)
                    drawLine(
                        color = GlowBlue.copy(alpha = 0.6f),
                        start = androidx.compose.ui.geometry.Offset(topX, topY),
                        end = androidx.compose.ui.geometry.Offset(bottomX, bottomY),
                        strokeWidth = 1.5.dp.toPx()
                    )
                    drawLine(
                        color = GlowBlue.copy(alpha = 0.6f),
                        start = androidx.compose.ui.geometry.Offset(leftX, leftY),
                        end = androidx.compose.ui.geometry.Offset(rightX, rightY),
                        strokeWidth = 1.5.dp.toPx()
                    )
                }
        )

        // Efeito de varredura (scanner line)
        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(y = scannerOffset.dp)
                .drawBehind {
                    // Linha horizontal brilhante
                    drawLine(
                        color = NeonBlue.copy(alpha = scannerAlpha),
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                        strokeWidth = 2.dp.toPx()
                    )

                    // Glow da linha
                    drawLine(
                        color = NeonBlue.copy(alpha = scannerAlpha * 0.5f),
                        start = androidx.compose.ui.geometry.Offset(-10.dp.toPx(), 0f),
                        end = androidx.compose.ui.geometry.Offset(size.width + 10.dp.toPx(), 0f),
                        strokeWidth = 6.dp.toPx()
                    )
                }
        )

        // Partículas flutuantes ao redor
        FloatingParticles()
    }
}

@Composable
fun FloatingParticles() {
    val infiniteTransition = rememberInfiniteTransition(label = "particles")

    repeat(6) { index ->
        val angle = (index * 60).toFloat()
        val offsetAnimation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 30f,
            animationSpec = infiniteRepeatable(
                animation = androidx.compose.animation.core.tween(
                    1500 + (index * 200),
                    easing = EaseInOutCubic
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "particle_$index"
        )

        val alphaAnimation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = androidx.compose.animation.core.tween(1500 + (index * 200)),
                repeatMode = RepeatMode.Reverse
            ),
            label = "particle_alpha_$index"
        )

        val radiusAngle = Math.toRadians(angle.toDouble())
        val offsetX = (offsetAnimation * Math.cos(radiusAngle)).dp
        val offsetY = (offsetAnimation * Math.sin(radiusAngle)).dp

        Box(
            modifier = Modifier
                .size(6.dp)
                .offset(x = offsetX, y = offsetY)
                .alpha(alphaAnimation)
                .drawBehind {
                    drawCircle(
                        color = NeonBlue,
                        radius = 3.dp.toPx()
                    )
                }
        )
    }
}

@Composable
fun SplashTextSection() {
    val infiniteTransition = rememberInfiniteTransition(label = "text_transition")

    val textAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(1500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "text_alpha"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "LootLog",
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            color = NeonBlue,
            modifier = Modifier.alpha(textAlpha)
        )

        Text(
            text = "Gerencie suas coleções",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = NeonBlue.copy(alpha = 0.7f),
            modifier = Modifier
                .alpha(textAlpha * 0.8f)
                .drawBehind {
                    // Underline brilhante
                    drawLine(
                        color = NeonBlue.copy(alpha = textAlpha * 0.5f),
                        start = androidx.compose.ui.geometry.Offset(0f, size.height + 8.dp.toPx()),
                        end = androidx.compose.ui.geometry.Offset(size.width, size.height + 8.dp.toPx()),
                        strokeWidth = 1.5.dp.toPx()
                    )
                }
        )

        // Loading indicator
        Box(
            modifier = Modifier
                .size(50.dp)
                .drawBehind {
                    val infiniteTransition = rememberInfiniteTransition(label = "loading")
                    val rotationAngle by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = androidx.compose.animation.core.tween(2000, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        ),
                        label = "loading_rotation"
                    )

                    drawArc(
                        color = NeonBlue,
                        startAngle = rotationAngle,
                        sweepAngle = 90f,
                        useCenter = false,
                        style = Stroke(width = 2.dp.toPx())
                    )
                    drawArc(
                        color = GlowBlue.copy(alpha = 0.5f),
                        startAngle = rotationAngle + 180f,
                        sweepAngle = 90f,
                        useCenter = false,
                        style = Stroke(width = 1.5.dp.toPx())
                    )
                }
        )
    }
}
