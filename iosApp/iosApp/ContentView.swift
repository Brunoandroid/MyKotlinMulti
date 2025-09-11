import SwiftUI
import shared

extension Color {
    init(hex: UInt, alpha: Double = 1.0) {
        let r = Double((hex >> 16) & 0xFF) / 255.0
        let g = Double((hex >> 8) & 0xFF) / 255.0
        let b = Double(hex & 0xFF) / 255.0
        self.init(.sRGB, red: r, green: g, blue: b, opacity: alpha)
    }
}

class LoginVM: ObservableObject {
    private let repo: LoginRepository = LoginRepository()
    @Published var showSuccess: Bool = false
    @Published var errorMessage: String? = nil

    func login(email: String, password: String) {
        let result = repo.login(email: email, password: password)
        if result is AuthLoginResultSuccess {
            showSuccess = true
            errorMessage = nil
        } else if let failure = result as? AuthLoginResultFailure {
            showSuccess = false
            errorMessage = failure.error.message
        }
    }
}

class HomeViewModel: ObservableObject {
    private let quoteRepository = QuoteRepository()

    @Published var quote: QuoteResponse?
    @Published var error: String?

    init() {
        fetchQuote()
    }

    func fetchQuote() {
        Task {
            do {
                let result = try await quoteRepository.getQuote()
                self.quote = result
            } catch {
                self.error = error.localizedDescription
            }
        }
    }
}

struct ContentView: View {
    @StateObject private var vm = LoginVM()
    @State private var email: String = ""
    @State private var password: String = ""
    @State private var isLoggedIn: Bool = false

    var body: some View {
        Group {
            if isLoggedIn {
                HomeView()
            } else {
                VStack(alignment: .center, spacing: 20) {
                    Text("Entrar")
                        .font(.system(size: 28, weight: .semibold))
                        .foregroundColor(Color(hex: 0x2C2C2C))
                        .frame(maxWidth: .infinity, alignment: .center)
                        .padding(.bottom, 4)

                    VStack(spacing: 12) {
                        TextField("Telefone/Email", text: $email)
                            .keyboardType(.emailAddress)
                            .textInputAutocapitalization(.never)
                            .autocorrectionDisabled()
                            .padding(14)
                            .background(Color.white)
                            .cornerRadius(24)

                        PasswordField(password: $password)
                    }

                    Button(action: {
                        vm.login(email: email, password: password)
                    }) {
                        Text("Entrar")
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .frame(height: 52)
                            .background(Color(hex: 0x2C2C2C))
                            .cornerRadius(24)
                    }

                    if let error = vm.errorMessage {
                        Text(error)
                            .foregroundColor(.red)
                    }

                    Spacer()
                }
                .padding(.horizontal, 20)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .background(Color(hex: 0xF5F3EF))
            }
        }
        .onChange(of: vm.showSuccess) { newValue in
            if newValue { isLoggedIn = true }
        }
        .alert("Login Efetuado", isPresented: $vm.showSuccess) {
            Button("OK", role: .cancel) { }
        }
    }
}

struct HomeView: View {
    @StateObject private var viewModel = HomeViewModel()

    var body: some View {
        VStack {
            Spacer()
            if let quote = viewModel.quote {
                Text(quote.quoteText)
                    .font(.system(size: 24, weight: .semibold))
                    .foregroundColor(Color(hex: 0x2C2C2C))
                    .multilineTextAlignment(.center)
                    .padding(.bottom, 8)
                    .padding(.horizontal, 16)
                Text("- \(quote.quoteAuthor)")
                    .font(.system(size: 16))
                    .foregroundColor(Color(hex: 0x2C2C2C))
                Spacer().frame(height: 16)
                Button(action: { viewModel.fetchQuote() }) {
                    Image(systemName: "arrow.clockwise")
                        .foregroundColor(Color(hex: 0x2C2C2C))
                        .frame(width: 56, height: 56)
                        .background(Color.clear)
                        .clipShape(Circle())
                        .overlay(
                            Circle()
                                .stroke(Color(hex: 0x2C2C2C), lineWidth: 2)
                        )
                }
                .buttonStyle(PlainButtonStyle())
            } else if let error = viewModel.error {
                Text("Erro: \(error)")
                    .foregroundColor(.red)
            } else {
                ProgressView()
            }
            Spacer()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .padding(16)
        .background(Color(hex: 0xF5F3EF))
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

struct PasswordField: View {
    @Binding var password: String
    @State private var isVisible: Bool = false

    var body: some View {
        ZStack(alignment: .trailing) {
            Group {
                if isVisible {
                    TextField("Senha", text: $password)
                        .padding(14)
                        .background(Color.white)
                        .cornerRadius(24)
                } else {
                    SecureField("Senha", text: $password)
                        .padding(14)
                        .background(Color.white)
                        .cornerRadius(24)
                }
            }
            Button(action: { isVisible.toggle() }) {
                Text(isVisible ? "Ocultar" : "Mostrar")
            }
            .padding(.trailing, 16)
        }
    }
}