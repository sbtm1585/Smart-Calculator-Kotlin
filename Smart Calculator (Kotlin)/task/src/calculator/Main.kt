package calculator

fun main() {
    val calc = Calculator()
    val variables = Variables
    while (true) {
        val input = readln().trimStart()
        when {
            input.trim() in variables.variables.keys -> println(variables.getVariableValue(input.trim()))
            input.matches(regexVar) ||
                    input.matches(regexIdentifier) ||
                    input.matches(regexUnknownVar) -> variables.createVariable(input)
            input.isEmpty() -> continue
            input == "/exit" -> { println("Bye"); break }
            input == "/help" -> println("The program calculates the sum of numbers")
            input.matches(regexNumOnly) -> println(input.replace("\\+".toRegex(), ""))
            input.matches(regex) -> calc.processInput(input)
            else ->
                if (input.startsWith("/")) {
                    errorMessages(4)
                } else {
                    if (input.substring(0, input.indexOf(' ')).matches(regexIdentifier)) {
                        errorMessages(1)
                    } else {
                        errorMessages(5)
                    }
                }
        }
    }
}