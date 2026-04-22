# LootLog - Gerenciador de Coleções Android

Um aplicativo Android moderno para gerenciar coleções de itens colecionáveis com monetização agressiva porém orgânica via RevenueCat e AdMob.

## 📋 Características

- **Gestão de Coleções**: Adicione, visualize e gerencie seus itens colecionáveis
- **Limite de Itens Gratuito**: Usuários free podem adicionar até 15 itens
- **Paywall Automático**: Ao tentar adicionar o 16º item, o app navega automaticamente para a tela de Premium
- **Persistência Local**: Dados armazenados offline com Room Database
- **Interface Moderna**: Jetpack Compose com Material 3
- **Monetização**: Integração pronta para RevenueCat (Premium) e AdMob (Anúncios)

## 🏗️ Arquitetura

```
Clean Architecture + MVVM
├── Domain Layer (Use Cases, Models)
├── Data Layer (Repositories, DAOs, Database)
└── UI Layer (Composables, ViewModels, Navigation)
```

## 🛠️ Tech Stack

- **Language**: Kotlin 2.0.10
- **UI**: Jetpack Compose + Material 3
- **Database**: Room 2.6.1
- **DI**: Hilt 2.51
- **Navigation**: Compose Navigation
- **Image Loading**: Coil 2.7.0
- **Monetization**: 
  - RevenueCat 7.13.2 (Subscriptions)
  - Google Mobile Ads 22.6.0 (Banner + Intersticial)

## 📱 Fluxo Principal (Limite de 15 Itens Free)

```
HomeScreen (Lista de Itens)
    ↓ [FAB Adicionar]
    ├─ Se < 15 itens → AddItemScreen
    │   ↓
    │   HomeScreen (Item adicionado)
    │
    └─ Se = 15 itens → PaywallScreen (Automático)
        ↓
        [Inscrever] ou [Restaurar Compras]
```

## 🚀 Getting Started

### Pré-requisitos
- Android Studio Hedgehog (2023.1.1) ou superior
- JDK 11+
- Android SDK 24+

### Configuração Rápida

1. **Clone o repositório**:
```bash
git clone <repo-url>
cd LootLog
```

2. **Abra no Android Studio**:
```bash
open -a /Applications/Android\ Studio.app .
```

3. **Sincronize as dependências**:
   - Android Studio fará isso automaticamente ao abrir o projeto
   - Ou use `./gradlew assemble`

4. **Configure as chaves (Opcional - para compilar com todas as features)**:
   - **RevenueCat**: Adicione a chave no futuro `RevenueCatModule.kt`
   - **AdMob**: Substitua o Application ID no `AndroidManifest.xml` (linha ~27)

5. **Compile e rode**:
```bash
./gradlew installDebug
```

Ou pressione `Run` no Android Studio.

## 📁 Estrutura do Projeto

```
app/src/main/
├── kotlin/com/cicerogusta/lootlog/
│   ├── data/
│   │   ├── dao/                    # Room DAOs
│   │   ├── database/               # Room Database
│   │   ├── model/                  # Entidades
│   │   └── repository/             # Repositories
│   ├── domain/
│   │   ├── model/                  # Modelos de negócio
│   │   └── usecase/                # Use Cases & Subscription
│   ├── di/                         # Hilt Modules
│   ├── ui/
│   │   ├── components/             # Composables reutilizáveis
│   │   ├── navigation/             # Navegação
│   │   ├── screen/
│   │   │   ├── home/              # Home + ViewModel
│   │   │   ├── add/               # Adicionar Item
│   │   │   └── paywall/           # Paywall
│   │   └── theme/                 # Material 3 Theme
│   ├── LootLogApp.kt              # App class com Hilt
│   └── MainActivity.kt
└── res/                           # Recursos (strings, colors, xml)
```

## 🔑 Limites e Funcionalidades

| Feature | Free | Premium |
|---------|------|---------|
| Adicionar Itens | 15 | Ilimitado |
| Visualizar Coleção | ✅ | ✅ |
| Anúncios | ✅ | ❌ |
| Backup na Nuvem | ❌ | ✅ (futuro) |
| Sincronização Multi-Device | ❌ | ✅ (futuro) |

## 🔄 Fluxo de Pagamento (Monetização)

### Usuário Free
1. Abre app → HomeScreen vazia
2. Clica FAB → AddItemScreen (15x permitido)
3. Após 15º item → FAB leva a PaywallScreen
4. Vê anúncios (banner na home, intersticial ao salvar)

### Usuário Premium
1. Após subscription via RevenueCat
2. Flag `isPremium = true` salvo em SharedPreferences
3. Limite removido → itens ilimitados
4. Sem anúncios

## ⚙️ Configuração das Dependências

Todas as dependências estão gerenciadas via **gradle/libs.versions.toml**:

```toml
# Altere versões centralizadas em um lugar
revenuecat = "7.13.2"
googleMobileAds = "22.6.0"
# etc...
```

## 🧪 Testing (Futura Implementação)

```bash
./gradlew test          # Unit tests
./gradlew connectedAndroidTest  # Instrumented tests
```

## 📝 Próximos Passos

- [ ] Integrar RevenueCat SDK completamente
- [ ] Integrar Google Mobile Ads
- [ ] Implementar camera/galeria para fotos
- [ ] Backup na nuvem (Firebase)
- [ ] Sincronização multi-device
- [ ] Dark mode completo
- [ ] Unit & UI tests

## 🐛 Troubleshooting

### Erro de compilação "unresolved reference to CollectibleItem"
- Verifique se todos os arquivos estão sincronizados: `Build` → `Rebuild Project`

### AdMob não aparece
- Substitua `ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy` no AndroidManifest.xml

### RevenueCat não funciona
- Chaves ainda não configuradas - será implementado em PR futura

## 📄 Documentação Adicional

Veja **CLAUDE.md** para detalhes da arquitetura e fluxos internos.

## 📞 Suporte

Para issues ou perguntas, abra uma issue no repositório GitHub.

## 📄 Licença

Este projeto é privado e desenvolvido por Cícero Gusta.

---

**Status**: ✅ Versão 1.0.0 - Estrutura base pronta para compilar e testar fluxo de paywall
