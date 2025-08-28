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
    var body: some View {
        VStack {
            Spacer()
            HStack {
                Spacer()
                Text("Hello World")
                    .font(.largeTitle)
                Spacer()
            }
            Spacer()
        }
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}