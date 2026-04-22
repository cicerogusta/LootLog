# ✨ Splash Screen - Animações Épicas

Documentação da tela de carregamento com animações avançadas do Compose.

## 🎨 Design & Cores

### Paleta Dark Mode + Neon Blue

```
🌑 Background:     #0A0E27 (Deep Space Black)
🟦 Primary Surface: #1A1F3A (Dark Card)
⚡ Neon Blue:      #00D9FF (Cyan Brilhante)
🔵 Glow Blue:      #0088FF (Azul Profundo)
🌈 Gradients:      Vertical + Radial (efeito 3D)
```

### Cores Personalizadas

```kotlin
object SplashColors {
    val DarkBackground = Color(0xFF0A0E27)
    val NeonBlue = Color(0xFF00D9FF)
    val GlowBlue = Color(0xFF0088FF)
}

object NeonPalette {
    val PrimaryNeon = Color(0xFF00D9FF)
    val SecondaryNeon = Color(0xFF00FF88)
    val AccentCyan = Color(0xFF00FFFF)
}
```

---

## 🎬 Animações Implementadas

### 1. **Diamante Rotativo**
```
┌─────────────────┐
│      ◇          │
│    ╱ ╲        │
│   ╱   ╲       │  ← Rotação 360°
│  ╱  💎  ╲      │     4 segundos
│ ╱       ╲     │     Linear
│╱─────────╲    │
└─────────────────┘

Rotação: 0° → 360° (4s, LinearEasing)
```

### 2. **Scanner de Varredura**
```
─────────────────
━━━━━━━━━━━━━━━━━  ← Linha brilhante
─────────────────

Movimento Vertical: -150dp → +150dp (2s, EaseInOutCubic)
Alpha: 0 → 1 (pulsante)
```

### 3. **Glow Pulsante (Aura)**
```
    ·····
  ··  💎  ··        ← Aura expandindo/retraindo
  ·  ····  ·
    ·····

Alpha: 0.3 → 1 (1.5s, EaseInOutCubic)
```

### 4. **Partículas Flutuantes**
```
      ●
   ●    ●         ← 6 partículas
   
💎

   ●    ●
      ●

Movimento: Circular (offset 30dp)
Duração: 1500ms + offset de delay
```

### 5. **Loading Spinner**
```
    ◯⭕ ◯
  ◯     ◯          ← 2 arcos girando
    ◯⭕ ◯

Rotação: 0° → 360° (2s)
Cores: NeonBlue + GlowBlue
```

### 6. **Fade de Texto**
```
LootLog
━━━━━━━━━━

Alpha pulsante (0.6 → 1)
Underline brilhante
```

---

## 🏗️ Estrutura do Código

### Arquivo Principal
```kotlin
app/src/main/kotlin/.../screen/splash/SplashScreen.kt
```

### Componentes

#### `SplashScreen(onNavigateNext: () -> Unit)`
- Entry point
- Timer de 4 segundos
- Navega para Auth ou Home

#### `SplashContent()`
- Fundo com gradient
- Coluna centralizada

#### `DiamondScanner()`
- Diamante com facetas
- Scanner line
- Efeito glow

#### `FloatingParticles()`
- 6 partículas ao redor
- Movimento circular

#### `SplashTextSection()`
- Título "LootLog"
- Subtítulo
- Loading spinner

---

## ⏱️ Timeline de Animações

```
0s   → SplashScreen inicia
0s   → Diamante começa rotação (4s)
0s   → Scanner começa varredura (2s, repetindo)
0s   → Glow começa pulsação (1.5s, repetindo)
0s   → Partículas começam movimento (1500ms+ cada)
0s   → Texto fade in
0s   → Loading spinner gira (2s, repetindo)

2s   → Scanner completa primeiro ciclo
3s   → Glow completa segundo ciclo

4s   → SplashScreen desaparece
4s   → Navega para Auth ou Home
4.5s → Animações param
```

---

## 🎯 Fluxo de Navegação

```
App Start
    ↓
SplashScreen → Route.Splash
    ↓ (4 segundos)
    ├─ Se isLoggedIn → Home
    └─ Se !isLoggedIn → Auth
```

### Código de Navegação

```kotlin
NavHost(
    startDestination = Route.Splash.route
) {
    composable(Route.Splash.route) {
        SplashScreen(
            onNavigateNext = {
                navController.navigate(
                    if (isLoggedIn) Route.Home.route else Route.Auth.route
                ) {
                    popUpTo(Route.Splash.route) { inclusive = true }
                }
            }
        )
    }
}
```

---

## 🎨 Customizações Possíveis

### Mudar Duração
```kotlin
// Em SplashScreen.kt
delay(4000) // Altere para 3000 (3s) ou 5000 (5s)
```

### Mudar Cores
```kotlin
// Em SplashScreen.kt
private val NeonBlue = Color(0xFF00D9FF) // Altere aqui
private val GlowBlue = Color(0xFF0088FF)
```

### Mudar Diamante para Outra Forma

#### Hexágono:
```kotlin
// Desenhar 6 pontos em círculo (60° cada)
repeat(6) { i ->
    val angle = (i * 60).toFloat()
    // ... drawLine entre pontos
}
```

#### Card:
```kotlin
drawRect(
    color = NeonBlue,
    size = Size(150.dp.toPx(), 150.dp.toPx()),
    style = Stroke(3.dp.toPx())
)
```

### Mudar Velocidade de Rotação
```kotlin
// Altere 4000 para velocidade desejada
tween(4000, easing = LinearEasing) // 4 segundos
tween(2000, easing = LinearEasing) // 2 segundos (mais rápido)
```

---

## 💻 Recursos Técnicos

### Compose APIs Usadas

| API | Uso |
|-----|-----|
| `rememberInfiniteTransition()` | Animações contínuas |
| `animateFloat()` | Animação de valores |
| `drawBehind` | Desenho customizado |
| `Brush.verticalGradient()` | Fundo gradiente |
| `Brush.radialGradient()` | Glow efeito radial |
| `LaunchedEffect` | Trigger delay (4s) |
| `Offset`, `rotate`, `alpha` | Modificadores de posição |

### Easing Functions

```kotlin
LinearEasing         // Constante
EaseInOutCubic      // Suave entrada e saída
```

---

## 📱 Testando

### No Emulador

```bash
./gradlew installDebug
```

### Observar Animações

1. App abre → SplashScreen ✓
2. Diamante rotaciona suavemente ✓
3. Scanner varre de cima a baixo ✓
4. Aura pulsa (glow) ✓
5. Partículas flutuam ao redor ✓
6. Texto fade in/out ✓
7. Loading spinner gira ✓
8. Após 4s → Navega para Auth ✓

---

## 🐛 Troubleshooting

### Animações lentas
- Verificar desempenho do emulador
- Reduzir complexidade do gradiente
- Usar `Profile` → `Compose Layout Inspector`

### Diamante não aparece
- Verificar `drawBehind` math (toPx())
- Aumentar tamanho do Box

### Scanner linha não visível
- Aumentar `strokeWidth`
- Aumentar `scannerAlpha`

### App trava na splash
- Verificar `LaunchedEffect` delay
- Verificar `AuthRepository.isLoggedIn` carregando

---

## 🚀 Melhorias Futuras

- [ ] Soundfx (som ao escanear)
- [ ] Vibração ao scanear
- [ ] Animação de entrada da câmera (scan effect)
- [ ] Customização de duração por preferências
- [ ] Versão Light Mode alternativa

---

## 📚 Referências

- [Compose Animation Docs](https://developer.android.com/jetpack/compose/animation)
- [Canvas Drawing](https://developer.android.com/jetpack/compose/graphics/canvas)
- [Neon Design Trends](https://www.behance.net/search/projects/?search=neon)

---

**Status**: ✅ Splash Screen épica pronta! Mostra 4 segundos antes de navegar.
