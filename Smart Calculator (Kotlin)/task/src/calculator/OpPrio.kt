package calculator

enum class Prio(val op: String, val prio: Int) {
    PLUS("+", 1),
    MINUS("-", 1),
    DIVIDE("/", 2),
    MULTIPLY("*", 2),
    PARENTHESIS_L("(", 3),
    PARENTHESIS_R(")", 4);

    companion object {
        fun fromOp(op: String): Int {
            return try {
                Prio.values().first { it.op == op }.prio
            } catch (e: Exception) {
                0
            }
        }
    }
}

