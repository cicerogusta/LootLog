# 🔍 Code Quality & Analysis Guide

Documentação sobre como manter a qualidade do código do LootLog com análise estática, linting e testes.

## 📊 Ferramentas Configuradas

### 1. **Android Lint**
Sistema automático de análise do Android que verifica issues comuns.

**Como rodar:**
```bash
./gradlew lint
```

**Configuração:** `app/build.gradle.kts`
```kotlin
lint {
    checkReleaseBuilds = true
    abortOnError = false
    disable += listOf("MissingTranslation", "ExtraTranslation")
}
```

**Output:** `app/build/reports/lint-results.html`

---

### 2. **Detekt** 
Análise estática para Kotlin que detecta:
- Dead code
- Code smells
- Performance issues
- Style violations

**Como rodar:**
```bash
# Análise completa
./gradlew detekt

# Análise com relatório HTML
./gradlew detekt --output-format html

# Apenas para o módulo app
./gradlew app:detekt
```

**Configuração:** `detekt.yml`
- Regras customizadas
- Thresholds de complexidade
- Exclusões de padrões

**Output:** 
```
build/reports/detekt/detekt.html
build/reports/detekt/detekt.sarif
```

**Regras Ativadas:**
- ✅ UnusedImports
- ✅ UnusedVariable
- ✅ UnusedPrivateMember
- ✅ CyclomaticComplexity (max 15)
- ✅ NestingLevel (max 5)
- ✅ MaxLineLength (120 chars)
- ✅ TooGenericExceptionCaught

---

### 3. **Unit Tests**
Testes para lógica de negócio (domain layer).

**Como rodar:**
```bash
# Rodar todos os testes
./gradlew test

# Rodar testes com coverage
./gradlew testDebugUnitTest

# Rodar teste específico
./gradlew test --tests CheckSubscriptionUseCaseTest
```

**Localização:** `app/src/test/kotlin/`

**Exemplo de teste:**
```kotlin
@Test
fun `when user has less than 15 items, canAddMoreItems should be true`() {
    // Arrange
    every { mockItemRepository.getItemCount() } returns flowOf(10)
    
    // Act & Assert
    // Implementation here
}
```

**Dependências:**
- JUnit 4
- Mockk (mock library)
- Coroutines Test

---

### 4. **Instrumented Tests** (Android)
Testes que rodam no emulador/device real.

**Como rodar:**
```bash
# Conecte um device ou inicie um emulador
./gradlew connectedAndroidTest

# Teste específico
./gradlew connectedAndroidTest --tests HomeScreenTest
```

**Localização:** `app/src/androidTest/kotlin/`

---

## 🔧 Workflow Recomendado

### Antes de Commitar

```bash
# 1. Formatar código (se usando ktlint)
./gradlew ktlintFormat

# 2. Rodar linting
./gradlew lint

# 3. Análise estática
./gradlew detekt

# 4. Testes unitários
./gradlew test

# 5. (Opcional) Testes no emulador
./gradlew connectedAndroidTest
```

### Comando Rápido (All-in-One)

```bash
./gradlew clean lint detekt test
```

### Pre-commit Hook (Git)

Crie `.git/hooks/pre-commit`:
```bash
#!/bin/bash
./gradlew lint detekt test || exit 1
```

---

## 📈 Code Coverage

Para gerar relatório de coverage:

```bash
./gradlew testDebugUnitTest --rerun-tasks

# Verificar relatório em:
# app/build/reports/coverage/
```

**Target de Coverage:**
- Domain Layer: 80%+
- Data Layer: 60%+
- UI Layer: 40%+ (difícil testar Compose)

---

## 🚨 Fixing Common Issues

### UnusedImports
```bash
./gradlew detekt --include-rules UnusedImports
```
**Fix:** IDE → Code → Optimize Imports

### CyclomaticComplexity > 15
**Indicação:** Função muito complexa, quebrar em funções menores

### NestingLevel > 5
**Indicação:** Muito aninhamento, usar early returns

### MaxLineLength > 120
**Fix:** Quebrar linhas longas

---

## 📝 Best Practices

✅ **DO:**
- Escrever testes para lógica importante
- Rodar lint antes de push
- Revisar relatório detekt periodicamente
- Manter imports limpos
- Usar tipos específicos (não `Any` ou `Exception`)

❌ **DON'T:**
- Commitar código com warnings
- Ignorar resultados de detekt
- Deixar código morto (imports, variáveis não usadas)
- Funções muito complexas (>15 cyclomatic complexity)
- Linhas muito longas (>120 chars)

---

## 🎯 CI/CD Integration

### GitHub Actions (Exemplo)

```yaml
name: Code Quality

on: [push, pull_request]

jobs:
  quality:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '11'
      - run: ./gradlew lint detekt test
```

---

## 📚 Recursos

- [Android Lint Documentation](https://developer.android.com/studio/write/lint)
- [Detekt Rules](https://detekt.dev/rules.html)
- [JUnit 4](https://junit.org/junit4/)
- [Mockk](https://mockk.io/)

---

## 🔄 Análise Periódica

**Mensal:**
- Revisar Detekt report
- Atualizar regras se necessário
- Analisar coverage trends

**Release:**
- Zero lint warnings
- Detekt clean
- Coverage > 70% (target)
- Todos os testes passando

---

**Status**: ✅ Code Quality Tools Ready!
