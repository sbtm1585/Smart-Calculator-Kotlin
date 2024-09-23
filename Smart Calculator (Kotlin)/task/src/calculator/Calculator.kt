package calculator

import java.math.BigInteger
import kotlin.reflect.KFunction2

class Calculator {

    private var listOfNumbers = mutableListOf<String>()

    fun processInput(arg: String) {
        var parantLeftCount = 0
        var parantRightCount = 0
        listOfNumbers.clear()
        listOfNumbers = buildString {
            arg.forEach {
                when (it) {
                    '(' -> { append("$it "); parantLeftCount++ }
                    ')' -> { append(" $it"); parantRightCount++ }
                    else -> append(it.toString())
                }
            }
        }.split(" ").map { it.trim() }.toMutableList()

        if (parantLeftCount != parantRightCount) {
            errorMessages(5)
            return
        }

        val operators = mutableSetOf<String>()
        val elementStr = StringBuilder()
        val list = buildList {
            listOfNumbers.forEach { element ->
                element.chunked(2).map { op ->
                    when {
                        op.matches(regexPlus) -> operators.add("+")
                        op.matches(regexMinus) -> operators.add("-")
                        op.matches(regexInvalid) -> { errorMessages(5); return }
                        else -> elementStr.append(op)
                    }
                }
                if (elementStr.toString().isNotEmpty()) add(elementStr.toString()); elementStr.clear()
                if (operators.size > 1) operators.remove("+")
                if (operators.isNotEmpty()) add(operators.first().also { operators.clear() })
            }
        }
        val postfix = convertToPostfix(list)
        startOperationPostfix(postfix)
    }

    private fun startOperationPostfix(list: List<String>) {
        val stack = ArrayDeque<String>()
        for (i in list) {
            when (i.toBigIntegerOrNull()) {
                is BigInteger -> stack.add(i)
                null -> when (i) {
                    "+", "-", "*", "/" -> stack.apply {
                        val operator = getOperand(i)
                        add(operator(this.removeAt(lastIndex - 1).toBigInteger(),
                            this.removeAt(lastIndex).toBigInteger()).toString())
                    }
                    else -> stack.apply { Variables.getVariableValue(i)
                        .also { addLast(it.toString()) } }
                }
            }
        }
        val result = stack.first()
        println(result)
    }

    private fun getOperand(item: String): KFunction2<BigInteger, BigInteger, BigInteger> {
        when(item) {
            "+" -> return Operands::add
            "-" -> return Operands::subtract
            "*" -> return Operands::multiply
            "/" -> return Operands::divide
        }
        return Operands::add
    }

    private fun convertToPostfix(list: List<String>): List<String> {
        val stack = ArrayDeque<String>()
        val postfix = ArrayDeque<String>()
        val rpnIter = list.iterator()

        while (rpnIter.hasNext()) {
            val i = rpnIter.next()
            when (Prio.fromOp(i)) {
                4 -> {
                    do {
                         postfix.addLast(stack.removeLast())
                    } while (stack[stack.size - 1] != "(")
                    stack.removeLast()
                    if (i != ")") stack.addLast(i)
                }
                3 -> stack.addLast(i)
                2, 1 ->
                    if (stack.isEmpty()) {
                        stack.addLast(i)
                    } else if (stack[stack.size - 1] == "(") {
                        stack.addLast(i)
                    } else if (Prio.fromOp(stack[stack.size - 1]) < Prio.fromOp(i)) {
                        stack.addLast(i)
                    } else if (Prio.fromOp(stack[stack.size - 1]) >= Prio.fromOp(i)) {
                        var a = ""
                        while (stack.isNotEmpty() && Prio.fromOp(stack[stack.size - 1]) >= Prio.fromOp(i)
                                && stack[stack.size - 1] != "(") {
                            a = stack.removeLast()
                            postfix.addLast(a)
                        }
                        stack.addLast(i)

                        if (stack.isEmpty()) {
                            stack.addLast(i)
                        } else {
                            while (stack.isNotEmpty() && (Prio.fromOp(a) < Prio.fromOp(i)
                                    && stack[stack.size - 1] != "(")) {
                                a = stack.removeLast()
                                postfix.addLast(a)
                            }
                            if (stack.isEmpty()) {
                                stack.add(i)
                            }
                        }
                    }
                0 -> postfix.addLast(i)
            }
        }
        while (stack.iterator().hasNext()) {
            postfix.addLast(stack.removeLast())
        }
        return postfix
    }

    object Operands {
        fun add(a: BigInteger, b: BigInteger) = a + b
        fun subtract(a: BigInteger, b: BigInteger) = a - b
        fun multiply(a: BigInteger, b: BigInteger) = a * b
        fun divide (a: BigInteger, b: BigInteger) = a / b
    }
}