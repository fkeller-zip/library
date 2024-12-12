package com.example.kotlin_classes.abschluss_abgabe

// 1. Data Class für Bücher
data class Book(
    val title: String,
    val author: String,
    val genre: Genre,
    var status: BookStatus
)

// 2. Enum Class für Genre
enum class Genre(val description: String) {
    FICTION("Fictional stories and novels"),
    NON_FICTION("Factual books and biographies"),
    SCIENCE("Books about science and technology"),
    HISTORY("Books that explore historical events"),
    CHILDREN("Books intended for children");

    fun printDescription() {
        println(description)
    }
}

// 3. Sealed Class für Buchstatus
sealed class BookStatus {
    object Available : BookStatus() {
        override fun printStatus() = "The book is available."
    }
    data class CheckedOut(val dueDate: String) : BookStatus() {
        override fun printStatus() = "The book is checked out, due on $dueDate."
    }
    data class Reserved(val reservedBy: String) : BookStatus() {
        override fun printStatus() = "The book is reserved by $reservedBy."
    }

    abstract fun printStatus(): String
}

// 4. Library-Klasse mit Nested und Inner Classes
class Library {
    private val books = mutableListOf<Book>()
    private val members = mutableListOf<LibraryMember>()
    private lateinit var address: LibraryAddress

    // Nested Class LibraryAddress
    data class LibraryAddress(val street: String, val city: String, val zipCode: String) {
        fun printAddress() {
            println("Library Address: $street, $city, $zipCode")
        }
    }

    // Inner Class LibraryMember
    inner class LibraryMember(val name: String, val memberID: String) {
        fun checkoutBook(book: Book, dueDate: String) {
            book.status = BookStatus.CheckedOut(dueDate)
        }

        fun reserveBook(book: Book) {
            book.status = BookStatus.Reserved(name)
        }
    }

    fun addBook(book: Book) {
        books.add(book)
    }

    fun searchBooks(query: String): List<Book> {
        return books.filter { it.title.contains(query, true) || it.author.contains(query, true) }
    }

    fun displayBooksStatus() {
        books.forEach { println("Title: ${it.title}, Status: ${it.status.printStatus()}") }
    }

    fun setAddress(street: String, city: String, zipCode: String) {
        address = LibraryAddress(street, city, zipCode)
    }

    fun printLibraryAddress() {
        address.printAddress()
    }
}

// 5. Implementierung der main()-Funktion
fun main() {
    val library = Library()

    // Adresse hinzufügen und ausgeben
    library.setAddress("123 Library St.", "Booktown", "12345")
    library.printLibraryAddress()

    // Bücher hinzufügen
    val book1 = Book("The Great Adventure", "John Doe", Genre.FICTION, BookStatus.Available)
    val book2 = Book("Science for Kids", "Jane Smith", Genre.CHILDREN, BookStatus.Available)
    val book3 = Book("History of the World", "Alice Brown", Genre.HISTORY, BookStatus.Available)

    library.addBook(book1)
    library.addBook(book2)
    library.addBook(book3)

    // Mitglieder hinzufügen
    val member = library.LibraryMember("Bob", "M123")

    // Buch ausleihen und reservieren
    member.checkoutBook(book1, "2024-12-19")
    member.reserveBook(book2)

    // Bücherstatus anzeigen
    library.displayBooksStatus()

    // Bücher durchsuchen
    val searchResults = library.searchBooks("Science")
    println("Search results:")
    searchResults.forEach { println(it.title) }
}
