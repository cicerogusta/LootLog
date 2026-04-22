# 💰 Guia de Monetização - LootLog

Este documento descreve como integrar RevenueCat e AdMob com o LootLog.

## 📊 Modelo de Monetização

### Free Tier
- **Limite**: 15 itens
- **Monetização**: Banner AdMob na HomeScreen
- **Gatilho de Paywall**: Ao tentar adicionar o 16º item

### Premium Tier
- **Itens Ilimitados**
- **Sem anúncios**
- **Backup na nuvem** (futuro)
- **Sincronização multi-device** (futuro)

---

## 🎯 RevenueCat Integration (Próxima Fase)

### 1. Setup Inicial

1. **Crie conta em [RevenueCat.com](https://www.revenuecat.com)**
2. **Adicione seu app Android**
3. **Obtenha o API Key público**

### 2. Instalar SDK

Já incluído em `libs.versions.toml`:
```kotlin
implementation(libs.revenuecat.purchases)
```

### 3. Inicializar no App Launch

Crie `RevenueCatModule.kt`:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object RevenueCatModule {

    @Singleton
    @Provides
    fun initializePurchases(@ApplicationContext context: Context) {
        Purchases.debugLogsEnabled = BuildConfig.DEBUG
        Purchases.configure(
            context,
            apiKey = "sua_api_key_publica"
        )
    }
}
```

Configure em `LootLogApp.kt`:

```kotlin
@HiltAndroidApp
class LootLogApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Hilt injetará e inicializará RevenueCat automaticamente
    }
}
```

### 4. Atualizar SubscriptionRepository

Integrar com RevenueCat real:

```kotlin
@Singleton
class SubscriptionRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val _isPremium = MutableStateFlow(loadPremiumStatus())

    fun updatePremiumStatus() {
        viewModelScope.launch {
            try {
                val customerInfo = Purchases.sharedInstance.customerInfo
                val isPremium = customerInfo.activeSubscriptions.isNotEmpty()
                sharedPreferences.edit()
                    .putBoolean(KEY_IS_PREMIUM, isPremium)
                    .apply()
                _isPremium.value = isPremium
            } catch (e: PurchasesException) {
                Log.e("RevenueCat", "Erro ao buscar status", e)
            }
        }
    }
}
```

### 5. Implementar Fluxo de Compra

Em `PaywallViewModel.kt`:

```kotlin
@HiltViewModel
class PaywallViewModel @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    fun subscribePremium(activity: Activity) {
        viewModelScope.launch {
            try {
                val offerings = Purchases.sharedInstance.offerings
                val monthlyPackage = offerings.current?.monthly

                if (monthlyPackage != null) {
                    Purchases.sharedInstance.purchasePackage(
                        activity,
                        monthlyPackage,
                        object : PurchaseCallback {
                            override fun onCompleted(storeTransaction: StoreTransaction, customerInfo: CustomerInfo) {
                                subscriptionRepository.setPremium(true)
                            }

                            override fun onError(error: PurchasesError) {
                                Log.e("Purchase", "Erro na compra", error.exception)
                            }
                        }
                    )
                }
            } catch (e: Exception) {
                Log.e("RevenueCat", "Erro ao iniciar compra", e)
            }
        }
    }

    fun restorePurchases(activity: Activity) {
        viewModelScope.launch {
            try {
                Purchases.sharedInstance.restorePurchases { customerInfo ->
                    val isPremium = customerInfo.activeSubscriptions.isNotEmpty()
                    subscriptionRepository.setPremium(isPremium)
                }
            } catch (e: Exception) {
                Log.e("RevenueCat", "Erro ao restaurar", e)
            }
        }
    }
}
```

### 6. Atualizar PaywallScreen

Passar Activity para os callbacks:

```kotlin
@Composable
fun PaywallScreen(
    onBackClick: () -> Unit,
    onSubscribeClick: () -> Unit,
    onRestorePurchases: () -> Unit,
    viewModel: PaywallViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as? Activity

    Button(
        onClick = {
            activity?.let {
                viewModel.subscribePremium(it)
            }
        }
    ) {
        Text(stringResource(R.string.subscribe))
    }
}
```

---

## 📢 Google Mobile Ads Integration

### 1. Setup AdMob

1. **Crie conta em [AdMob.google.com](https://admob.google.com)**
2. **Adicione seu app Android**
3. **Gere IDs dos anúncios**

### 2. Configurar no Manifest

Em `AndroidManifest.xml`, substitua com seu Application ID:

```xml
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy" />
```

### 3. Criar Módulo de Ads

`AdModule.kt`:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AdModule {

    @Singleton
    @Provides
    fun initializeMobileAds(@ApplicationContext context: Context) {
        MobileAds.initialize(context)
    }

    @Provides
    fun provideBannerAdUnitId(): String = "ca-app-pub-3940256099942544/6300978111"  // Test ID

    @Provides
    fun provideInterstitialAdUnitId(): String = "ca-app-pub-3940256099942544/1033173712"  // Test ID
}
```

### 4. Banner Ad na HomeScreen

```kotlin
@Composable
fun BannerAdView(
    modifier: Modifier = Modifier,
    adUnitId: String = "ca-app-pub-3940256099942544/6300978111"
) {
    AndroidView(
        modifier = modifier.fillMaxWidth().height(50.dp),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                this.adUnitId = adUnitId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

@Composable
fun HomeScreen(
    // ... parâmetros
    viewModel: HomeViewModel = hiltViewModel()
) {
    val subscriptionState by viewModel.subscriptionState.collectAsState()

    Scaffold(
        // ... topBar e FAB
    ) { innerPadding ->
        Column {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                // ... items
            }

            // Mostrar banner apenas para usuários free
            if (!subscriptionState.isPremium) {
                BannerAdView(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )
            }
        }
    }
}
```

### 5. Intersticial ao Salvar Item

```kotlin
@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val itemRepository: CollectibleItemRepository,
    @Qualifier("interstitialAdUnitId") 
    private val interstitialAdUnitId: String
) : ViewModel() {

    private var interstitialAd: InterstitialAd? = null

    fun addItem(item: CollectibleItem) {
        viewModelScope.launch {
            itemRepository.insertItem(item)
            
            // Mostrar intersticial se free
            if (!isPremium()) {
                loadAndShowInterstitial()
            }
        }
    }

    private fun loadAndShowInterstitial() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, interstitialAdUnitId, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    interstitialAd?.show(activity)
                }
            }
        )
    }
}
```

---

## 🧪 Testing

### RevenueCat Sandbox
- Use IDs de teste para testar fluxo de compra
- Não cobra cartão real
- Simula diferentes tipos de assinatura

### AdMob Test Ads
```
Banner: ca-app-pub-3940256099942544/6300978111
Interstitial: ca-app-pub-3940256099942544/1033173712
Rewarded: ca-app-pub-3940256099942544/5224354917
```

---

## 📈 Métricas Recomendadas

1. **Taxa de Conversão**: Free → Premium
2. **ARPU**: Receita Média por Usuário
3. **Lifetime Value**: Valor do usuário ao longo da vida
4. **Retenção**: % de usuários ativos após N dias

---

## 🔒 Segurança

- **Nunca** exponha chaves privadas da RevenueCat no código
- Use **BuildConfig** para separar chaves dev/prod
- Valide recebos no backend quando possível

---

## 📞 Referências

- [RevenueCat Docs](https://docs.revenuecat.com/docs)
- [Google Mobile Ads Docs](https://developers.google.com/admob/android/quick-start)
- [Android Billing Library](https://developer.android.com/google-play/billing)

---

**Status**: 📝 Pronto para implementação em PR dedicado
