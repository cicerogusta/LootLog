# 🔐 Firebase Setup Guide - LootLog

## Configurar Firebase Authentication com Email/Senha e Google Sign-In

### 1️⃣ **Criar Projeto Firebase**

1. Acesse [Firebase Console](https://console.firebase.google.com)
2. Clique em "Criar projeto"
3. Nome: `LootLog`
4. Desabilitar Google Analytics (opcional)
5. Criar projeto

### 2️⃣ **Adicionar App Android ao Firebase**

1. No console, clique em "Adicionar app" → Android
2. **Package name**: `com.cicerogusta.lootlog`
3. **SHA-1 Certificate**: Obtenha com:
   ```bash
   ./gradlew signingReport
   ```
   Copie o SHA-1 do variant `debug`
4. Registrar app
5. **Baixar** `google-services.json`
6. Colocar em: `app/google-services.json`

### 3️⃣ **Atualizar build.gradle.kts (Root)**

Adicione o plugin do Google Services:

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.ksp) apply false
    id("com.google.gms.google-services") version "4.4.0" apply false  // ADD THIS
}
```

### 4️⃣ **Atualizar build.gradle.kts (App)**

Adicione no início (após plugins):

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    id("com.google.gms.google-services")  // ADD THIS
}
```

### 5️⃣ **Habilitar Email/Senha no Firebase**

1. Firebase Console → Seu Projeto
2. **Authentication** → **Sign-in method**
3. Habilitar: **Email/Senha**
4. Clique em "Salvar"

### 6️⃣ **Habilitar Google Sign-In**

1. **Authentication** → **Sign-in method**
2. Habilitar: **Google**
3. Copie o **Web Client ID** (você precisa disso!)
4. Salvar

### 7️⃣ **Configurar Google Sign-In no App**

1. Abra `app/src/main/kotlin/com/cicerogusta/lootlog/ui/components/GoogleSignInButton.kt`
2. Substitua `seu_web_client_id.apps.googleusercontent.com` pelo Web Client ID do Firebase

```kotlin
val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestIdToken("YOUR_WEB_CLIENT_ID.apps.googleusercontent.com")  // ← Substitua aqui!
    .requestEmail()
    .build()
```

### 8️⃣ **Compilar e Testar**

```bash
./gradlew installDebug
```

---

## 🧪 Testando Firebase Auth

### Email/Senha
1. Abra o app
2. Digite email: `teste@example.com`
3. Senha: `senha123!`
4. Clique em "Criar Conta"
5. Deve criar conta e fazer login automaticamente

### Google Sign-In
1. Clique em "Entrar com Google"
2. Selecione conta Google
3. Deve fazer login automaticamente

---

## 📋 Checklist Final

- [ ] `google-services.json` copiado para `app/`
- [ ] Plugin `com.google.gms.google-services` adicionado ao build.gradle.kts
- [ ] Email/Senha habilitado no Firebase Console
- [ ] Google Sign-In habilitado no Firebase Console
- [ ] Web Client ID copiado para GoogleSignInButton.kt
- [ ] App compilado com sucesso
- [ ] Login testado (Email e Google)

---

## 🔗 Próximas Integrações

Após setup completo:

1. **RevenueCat** - Sincronizar `userId` do Firebase com RevenueCat
2. **Firestore** - Sincronizar dados de itens para nuvem
3. **Cloud Storage** - Armazenar imagens dos itens

---

## 🐛 Troubleshooting

### "com.google.gms.google-services not found"
- Adicione o plugin ao `settings.gradle.kts`:
  ```kotlin
  id("com.google.gms.google-services") version "4.4.0" apply false
  ```

### "SHA-1 não reconhecido"
- Certifique-se de usar o SHA-1 do variant **debug**
- Execute: `./gradlew signingReport`
- Copie exatamente como aparece

### Google Sign-In retorna erro
- Verifique se o Web Client ID está correto
- Certifique-se de que o pacote `com.cicerogusta.lootlog` está registrado no Firebase

### Erro: "Falha ao criar conta"
- Verifique se o email já existe
- Senha precisa ter pelo menos 6 caracteres

---

**Status**: ✅ Firebase Auth pronto para integração completa
