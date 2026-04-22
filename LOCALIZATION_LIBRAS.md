# 🌍♿ Localização e LIBRAS - LootLog

Documentação completa sobre suporte multilíngue e acessibilidade em LIBRAS.

## 🌐 Idiomas Suportados

| Idioma | Código | Bandeira | Status |
|--------|--------|----------|--------|
| Português (Brasil) | `pt` | 🇧🇷 | ✅ Completo |
| English | `en` | 🇺🇸 | ✅ Completo |
| Español | `es` | 🇪🇸 | ✅ Completo |
| Français | `fr` | 🇫🇷 | ✅ Completo |
| Deutsch | `de` | 🇩🇪 | ✅ Completo |

### Adicionar Novo Idioma

1. **Criar arquivo de strings**:
   ```
   app/src/main/res/values-XX/strings.xml
   ```
   Onde `XX` é o código ISO do idioma (ex: `it` para Italiano)

2. **Traduzir strings**:
   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <resources>
       <string name="app_name">LootLog</string>
       <string name="home_title">Mia Raccolta</string>
       <!-- ... resto das strings -->
   </resources>
   ```

3. **Atualizar LocalizationManager**:
   ```kotlin
   fun getAvailableLanguages(): List<Language> {
       return listOf(
           Language("pt", "Português (Brasil)", "🇧🇷"),
           Language("en", "English", "🇺🇸"),
           Language("es", "Español", "🇪🇸"),
           Language("fr", "Français", "🇫🇷"),
           Language("de", "Deutsch", "🇩🇪"),
           Language("it", "Italiano", "🇮🇹")  // ← Adicione aqui
       )
   }
   ```

---

## ♿ LIBRAS - Língua Brasileira de Sinais

### O Que é LIBRAS?

LIBRAS é a língua natural de sinais utilizada pela comunidade surda brasileira. É uma língua visual-espacial com sintaxe própria, diferente da língua portuguesa.

### Implementação no App

#### 1. **Componente LibrasAccessibility**

Localização: `ui/components/LibrasAccessibility.kt`

```kotlin
@Composable
fun LibrasAccessibilityButton(
    isLibrasEnabled: Boolean,
    onToggle: (Boolean) -> Unit
)
```

Mostra um botão togglável para ativar/desativar LIBRAS.

#### 2. **LocalizationManager**

Gerencia:
- Seleção de idioma
- Estado de LIBRAS
- Persistência em SharedPreferences

```kotlin
fun setLibrasEnabled(enabled: Boolean)
fun toggleLibras()
```

#### 3. **SettingsScreen**

Onde o usuário pode:
- Selecionar idioma
- Ativar/desativar LIBRAS
- Ver informações sobre acessibilidade

---

## 📱 Fluxo de Uso - Localização

```
App Launch
    ↓
LocalizationManager carrega preferências (SharedPreferences)
    ↓
Aplica idioma padrão do device OU último selecionado
    ↓
HomeScreen
    → Icone Settings (engrenagem)
    → Clica Settings
    → SettingsScreen
        - RadioButtons com idiomas
        - Botão LIBRAS
        - Seleciona novo idioma
        - Aplica imediatamente
        - LocalizationManager salva preferência
```

---

## 🎯 Integração com Serviços de LIBRAS

### Opção 1: Hand Talk (Recomendado)

Hand Talk fornece interpretação automática em LIBRAS.

1. **Registre-se**: https://www.handtalk.me/pt-BR/
2. **Obtenha API key**
3. **Integre no app**:

```kotlin
// Em LibrasAccessibility.kt
suspend fun getLibrasInterpretation(text: String): String {
    return handTalkAPI.interpret(
        text = text,
        apiKey = "seu_api_key",
        targetLanguage = "pt-br"
    )
}
```

### Opção 2: Vídeos Pré-gravados

Armazene vídeos em LIBRAS para mensagens principais:

```
app/src/main/res/raw/
├── libras_welcome.mp4
├── libras_add_item.mp4
├── libras_premium.mp4
└── ...
```

### Opção 3: Web View com Intérprete

Use um Web View embarcado:

```kotlin
@Composable
fun LibrasVideoWebView(text: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                // Carregar iframe do Hand Talk
                loadUrl("https://widget.handtalk.me/...")
            }
        }
    )
}
```

---

## 💾 Persistência

LocalizationManager usa **SharedPreferences** para salvar:

```kotlin
companion object {
    private const val KEY_LANGUAGE = "selected_language"
    private const val KEY_LIBRAS_ENABLED = "libras_enabled"
}
```

**Arquivo**: `app/src/main/kotlin/.../domain/usecase/LocalizationManager.kt`

---

## 🎨 UI Components com Suporte a LIBRAS

### 1. AuthScreen
- Formulários em múltiplos idiomas
- Mensagens de erro localizadas

### 2. HomeScreen
- Título da coleção
- Botões de ação
- Mensagens vazias

### 3. AddItemScreen
- Labels de campos
- Placeholders
- Validações

### 4. PaywallScreen
- Benefícios Premium
- CTAs (Call-to-Actions)

---

## 🔧 Configuração de Ambiente

### strings.xml Padrão (Português)
```
values/strings.xml → Português (Brasil)
```

### strings.xml Localizados
```
values-pt-rBR/strings.xml → Português (Brasil) explícito
values-en/strings.xml → English
values-es/strings.xml → Español
values-fr/strings.xml → Français
values-de/strings.xml → Deutsch
```

### Locale Fallback
Se device está em Italiano (não suportado):
1. Android tenta `values-it/`
2. Fallback para `values/` (Português padrão)

---

## 📊 Fluxo Técnico

```
LocalizationManager (Singleton)
    ├─ currentLanguage: StateFlow<String>
    ├─ isLibrasEnabled: StateFlow<Boolean>
    ├─ getAvailableLanguages(): List<Language>
    ├─ setLanguage(code: String)
    └─ setLibrasEnabled(Boolean)
        ↓
    SettingsScreen (UI)
        ├─ RadioButtons (escolher idioma)
        └─ LibrasAccessibilityButton (toggle)
        ↓
    Locale.setDefault(locale)
    context.resources.updateConfiguration()
    ↓
    App recompõe com novo idioma
```

---

## 🧪 Testing

### Testar Mudança de Idioma

```kotlin
fun testLanguageChange() {
    // Simular clique em Espanhol
    localizationManager.setLanguage("es")
    
    // Verificar que é salvo
    assert(sharedPreferences.getString(KEY_LANGUAGE) == "es")
    
    // Verificar que UI atualiza
    assert(composableState.currentLanguage.value == "es")
}
```

### Testar LIBRAS

```kotlin
fun testLibrasToggle() {
    localizationManager.setLibrasEnabled(true)
    assert(isLibrasEnabled.value == true)
    
    // Verificar que componentes veem LIBRAS habilitado
    assert(librasButton.isEnabled)
}
```

---

## ♿ Acessibilidade Adicional

### TalkBack (Leitura de Tela)
- Todos os componentes têm `contentDescription`
- Strings são automaticamente lidas no idioma do device

### Texto Grande
- Suporta tamanhos de fonte até 200%
- Layout responsivo

### Cores de Alto Contraste
- Material 3 com temas acessíveis
- Modo escuro otimizado

---

## 📚 Recursos

- [Android Localization](https://developer.android.com/guide/topics/resources/localization)
- [Hand Talk API](https://www.handtalk.me)
- [LIBRAS - Wikipédia](https://pt.wikipedia.org/wiki/L%C3%ADngua_Brasileira_de_Sinais)
- [W3C: Accessibility](https://www.w3.org/WAI/)

---

## ✅ Checklist de Implementação

- [x] Strings em 5 idiomas (PT, EN, ES, FR, DE)
- [x] LocalizationManager com StateFlow
- [x] SettingsScreen com seleção de idioma
- [x] LIBRAS toggle no app
- [x] Persistência em SharedPreferences
- [ ] Integração com Hand Talk API
- [ ] Vídeos em LIBRAS pré-gravados
- [ ] Testes de localização
- [ ] Documentação de contribuição (como adicionar idiomas)

---

## 🚀 Próximos Passos

1. **Integrar Hand Talk**
   - Obter API key
   - Implementar interpretação de texto em LIBRAS
   - Mostrar vídeos em LibrasVideoViewer

2. **Adicionar Mais Idiomas**
   - Chinês, Japonês, Russo, etc.
   - Considerar RTL (direita para esquerda) para Árabe

3. **Otimizar Acessibilidade**
   - Voice commands
   - Gestures customizados
   - High contrast mode

4. **Documentação Específica**
   - Guia para desenvolvedores: "Como adicionar novo idioma"
   - Guia para tradutores: "Como traduzir strings"

---

**Status**: ✅ Sistema de i18n e LIBRAS pronto para expandir com integrações de serviço.
