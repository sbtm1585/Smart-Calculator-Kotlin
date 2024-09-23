package calculator

import java.math.BigInteger

object Variables {
    val variables = mutableMapOf<String, BigInteger>()

    fun createVariable(input: String) {
        val inputList = input.split("=").map { it.trim() }
        if (!checkInput(inputList)) return
        variables[inputList[0]] = inputList[1].toBigIntegerOrNull() ?: variables.getValue(inputList[1])
    }

    fun getVariableValue(key: String): BigInteger {
        if (key !in variables.keys) errorMessages(3)
        return variables.getValue(key)
    }

    private fun checkInput(input: List<String>): Boolean {
        if (input.size > 1) {
            when {
                input[1] !in variables.keys &&
                    input[1].matches(regexNotNum) -> { errorMessages(3); return false }
                input[1].matches(regexCheck1) &&
                    input[1] !in variables.keys ||
                    input.size > 2 -> { errorMessages(2); return false }
                input[0].matches(regexNum) || input[0].matches(regexIdentifier) -> {
                    errorMessages(1); return false
                }
                else -> return true
            }
        } else if (input.size == 1) {
            when {
                input[0].matches(regexNum) || input[0].matches(regexIdentifier) -> {
                errorMessages(1); return false
            }
                input[0].matches(regexUnknownVar) &&
                    input[0] !in variables.keys -> { errorMessages(3); return false }
                else -> return true
            }
        }
        return true
    }
}