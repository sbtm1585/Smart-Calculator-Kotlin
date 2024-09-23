package calculator

fun errorMessages(id: Int) {
    when(id) {
        1 -> println("Invalid identifier")
        2 -> println("Invalid assignment")
        3 -> println("Unknown variable")
        4 -> println("Unknown command")
        5 -> println("Invalid expression")
    }
}