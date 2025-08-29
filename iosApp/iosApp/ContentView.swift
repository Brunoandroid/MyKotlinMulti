import SwiftUI
import shared

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
                VStack(alignment: .leading, spacing: 16) {
                    Text("Login")
                        .font(.largeTitle)
                    TextField("Email", text: $email)
                        .textFieldStyle(.roundedBorder)
                        .keyboardType(.emailAddress)
                        .textInputAutocapitalization(.never)
                        .autocorrectionDisabled()
                    SecureField("Senha", text: $password)
                        .textFieldStyle(.roundedBorder)
                    Button("Fazer login") {
                        vm.login(email: email, password: password)
                    }
                    .buttonStyle(.borderedProminent)
                    if let error = vm.errorMessage { Text(error).foregroundColor(.red) }
                    Spacer()
                }
                .padding()
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
                if let quote = viewModel.quote {
                    Text(quote.quoteText)
                        .font(.title)
                        .padding()
                    Text("- \(quote.quoteAuthor)")
                        .font(.subheadline)
                        .padding()
                } else if let error = viewModel.error {
                    Text("Erro: \(error)")
                        .foregroundColor(.red)
                } else {
                    Button("Buscar uma citação") {
                        viewModel.fetchQuote()
                    }
                }
            }
        }

}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}