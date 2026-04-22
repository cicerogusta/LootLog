# LootLog - Gerenciador de Coleções Android

Um app Android moderno para gerenciamento de coleções de itens colecionáveis com monetização via RevenueCat e AdMob.

## Arquitetura

**Clean Architecture com MVVM:**
- **Domain**: Use cases e modelos de negócio
- **Data**: Repositories, DAOs, Database (Room)
- **UI**: ViewModels, Screens (Jetpack Compose)

## Estrutura do Projeto

```
app/src/
├── main/
│   ├── kotlin/com/cicerogusta/lootlog/
│   │   ├── data/
│   │   │   ├── dao/
│   │   │   ├── database/
│   │   │   ├── model/
│   │   │   └── repository/
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   └── usecase/
│   │   ├── di/
│   │   ├── ui/
│   │   │   ├── components/
│   │   │   ├── navigation/
│   │   │   ├── screen/
│   │   │   └── theme/
│   │   ├── LootLogApp.kt
│   │   └── MainActivity.kt
│   ├── res/
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Componentes Principais

### Data Layer
- **CollectibleItem**: Entidade Room para itens da coleção
- **CollectibleItemDao**: Operações de banco de dados
- **LootLogDatabase**: Configuração do Room
- **CollectibleItemRepository**: Abstração de dados

### Domain Layer
- **CheckSubscriptionUseCase**: Verifica limite de itens gratuitos (máx 15)
- **SubscriptionRepository**: Gerencia status premium do usuário
- **SubscriptionState**: Modelo com estado da assinatura

### UI Layer
- **HomeScreen**: Lista de itens com FAB para adicionar
- **PaywallScreen**: Tela de persuasão para upgrade
- **CollectibleItemCard**: Card reutilizável para item

## Fluxo de Limitação

1. Usuário tenta adicionar o 16º item
2. `HomeViewModel.onAddItemClicked()` verifica `SubscriptionState`
3. Se `canAddMoreItems = false` → navega para `PaywallScreen`
4. User clica em "Inscrever" → RevenueCat SDK (futura integração)

## Dependências Principais

- **Jetpack Compose**: UI moderna
- **Room**: Persistência local
- **Hilt**: Injeção de dependência
- **Navigation Compose**: Roteamento
- **RevenueCat**: Monetização via assinatura
- **Coil**: Carregamento de imagens
- **Google Mobile Ads**: Banner + Intersticial AdMob

## Próximos Passos

1. Integrar RevenueCat SDK (chaves de API)
2. Integrar Google Mobile Ads (ID da app)
3. Implementar AddItemScreen para cadastro de itens
4. Adicionar câmera/galeria para imagens
5. Implementar backup na nuvem
6. Configurar CI/CD

## Como Buildar

```bash
./gradlew build
./gradlew installDebug
```

## Limites Atuais

- Usuários free: máx 15 itens
- Usuários premium: itens ilimitados
- Dados persistidos localmente (Room)
- Status premium salvo em SharedPreferences
