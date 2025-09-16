### Visão geral
MyKoltinMulti é um projeto Kotlin Multiplatform Mobile (KMM) que compartilha regras de negócio entre Android e iOS. Ele demonstra um fluxo simples de autenticação e o consumo de uma API de citações (quotes), com UI nativa em cada plataforma:

- Android: Jetpack Compose (Material 3)
- iOS: SwiftUI
- Módulo compartilhado: lógica de autenticação, validação e repositórios (chamadas de rede e regras)

<img src="https://github.com/Brunoandroid/Imagens/blob/main/mykotlinmulti.gif" width="320" height="600"/>

### Principais funcionalidades
- Login com validação de email e senha compartilhada entre plataformas
- Tratamento de erros de autenticação padronizado via `LoginResult`
- Tela Home exibindo uma citação aleatória (`QuoteResponse`) buscada de um serviço HTTP
- Botão de refresh para buscar nova citação

### Stack técnica
- Kotlin Multiplatform (KMM) para compartilhamento de código
- Android: Jetpack Compose, Material 3, `ViewModel` + `StateFlow`
- iOS: SwiftUI, `ObservableObject`, `@Published`
- Camada de dados compartilhada: `Repository` + serviços de rede (`ApiService`)
- Coroutines (Kotlin) e interoperabilidade com Swift Concurrency (via `async/await` no iOS)

### Estrutura de pastas (resumo)
```
AndroidStudioProjects/
└─ MyKoltinMulti/
   ├─ androidApp/                      # Aplicativo Android
   │  ├─ src/main/java/com/example/mykoltinmulti/android/
   │  │  ├─ MainActivity.kt            # Raiz de composição e navegação simples
   │  │  ├─ ui/theme/Theme.kt          # Tema Material 3
   │  │  └─ screens/
   │  │     ├─ login/
   │  │     │  ├─ LoginScreen.kt       # Tela de login (Compose)
   │  │     │  └─ LoginViewModel.kt    # Chama `LoginRepository`
   │  │     └─ home/
   │  │        ├─ HomeScreen.kt        # Exibe citação, botão de refresh
   │  │        └─ HomeViewModel.kt     # Usa `QuoteRepository`
   │  └─ src/main/AndroidManifest.xml
   │
   ├─ iosApp/
   │  └─ iosApp/
   │     └─ ContentView.swift          # Fluxo de login e Home em SwiftUI
   │
   └─ shared/                          # Módulo compartilhado (KMM)
      └─ src/commonMain/kotlin/com/example/mykoltinmulti/
         ├─ repository/
         │  ├─ LoginRepository.kt      # Validação e autenticação fake
         │  └─ QuoteRepository.kt      # Chama `ApiService` para buscar citação
         ├─ auth/                      # (esperado) `AuthValidator`, `LoginResult`, `LoginError`
         └─ network/                   # (esperado) `ApiService`, modelos (`QuoteResponse`)
```

### Fluxo de autenticação
- `LoginScreen` (Android) e `ContentView` (iOS) capturam email e senha.
- `LoginViewModel` (Android) e `LoginVM` (iOS) invocam `LoginRepository` (compartilhado).
- `LoginRepository.login(email, password)` retorna:
    - `LoginResult.Success` em caso de sucesso
    - `LoginResult.Failure(LoginError.*)` para erros (email inválido, senha inválida, não autorizado)
- Regra atual (demo): senha correta é `"123456"`; validações de formato usando `AuthValidator`.

### Fluxo de cotações (quotes)
- `HomeViewModel` (Android) e `HomeViewModel` (iOS) consomem `QuoteRepository` (compartilhado).
- `QuoteRepository.getQuote()` delega para `ApiService` obter um `QuoteResponse`.
- Android expõe `StateFlow<QuoteResponse?>`; iOS usa `@Published` e `async/await`.

### Como executar (Android)
1. Pré-requisitos:
    - Android Studio Jellyfish ou superior, com plugin KMM
    - JDK 17 (ou conforme `gradle.properties`/`build.gradle` do projeto)
    - Android SDK atualizado
2. Abrir a pasta `MyKoltinMulti/` no Android Studio.
3. Selecionar a configuração `androidApp` e executar em um emulador ou device.

### Como executar (iOS)
1. Pré-requisitos:
    - Xcode 15+ (macOS)
    - CocoaPods (se o projeto utilizar integração via pods) e toolchain KMM configurada
2. Sincronizar o projeto via Android Studio (KMM) para gerar artefatos iOS.
3. Abrir o workspace/projeto iOS (se existir) ou rodar o target iOS via Android Studio KMM plugin.
4. Selecionar um simulador e executar.

Observação: Em KMM, o módulo `shared` é construído como framework para iOS; o Swift importa `shared` e usa as classes Kotlin expostas (como `LoginRepository`, `QuoteRepository` e modelos como `QuoteResponse`).

### Execução rápida (TL;DR)
- Android: abrir no Android Studio e rodar `androidApp`.
- iOS: gerar o framework `shared`, abrir o target iOS e rodar em simulador via Xcode/Android Studio KMM.
- Login de teste: qualquer email válido; senha `123456`.

### Contribuição
- Faça um fork do repositório
- Crie um branch a partir de `master`: `git checkout -b feat/nome-da-feature`
- Abra um Pull Request com descrição clara, screenshots (se aplicável) e itens de verificação


