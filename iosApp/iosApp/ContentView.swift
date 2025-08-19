import SwiftUI
import shared

class TodoVM: ObservableObject {
    private let repo = TodoRepository()
    @Published var items: [Todo] = []

    init() {
        refresh()
    }

    func refresh() {
        items = Array(repo.listArray())
    }

    func add(_ text: String) {
        let trimmed = text.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !trimmed.isEmpty else { return }
        _ = repo.add(text: trimmed)
        refresh()
    }

    func toggle(_ id: Int64) {
        _ = repo.toggle(id: id)
        refresh()
    }

    func remove(_ id: Int64) {
        _ = repo.remove(id: id)
        refresh()
    }

    func clear() {
        repo.clear()
        refresh()
    }
}

struct ContentView: View {
    @StateObject private var vm = TodoVM()
    @State private var input: String = ""

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("KMP To-Do (shared logic)")
                .font(.headline)
            HStack {
                TextField("New task", text: $input)
                    .textFieldStyle(.roundedBorder)
                Button("Add") {
                    vm.add(input)
                    input = ""
                }
            }
            List {
                ForEach(vm.items, id: \.id) { todo in
                    HStack {
                        Button(action: { vm.toggle(todo.id) }) {
                            Image(systemName: todo.done ? "checkmark.square" : "square")
                        }
                        Text(todo.text)
                            .strikethrough(todo.done)
                        Spacer()
                        Button("Delete") { vm.remove(todo.id) }
                            .foregroundColor(.red)
                    }
                }
            }
            Button("Clear All") { vm.clear() }
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