package calculator

// used in fun main()
val regex = """[-+*/]*\(?([a-zA-Z]|[0-9]+)\)?\s[*/+-]+\s+\(?([a-zA-Z]|[0-9]+)\)?.*""".toRegex()
val regexNumOnly = "[*/+-]*([a-zA-Z]|[0-9]*)[^-+*/]".toRegex()
val regexVar = "^[a-zA-Z]+\\s*=\\s*.*".toRegex()
val regexIdentifier = "[a-zA-Z]*?[0-9]+[a-zA-Z]*?".toRegex()
val regexUnknownVar = "^[a-zA-Z]+".toRegex()

// used in Calculator class
val regexPlus = "(-{2}|[+]{2})".toRegex()
val regexMinus = "(\\+-|-\\+|-)".toRegex()
val regexInvalid = "([*]\\D+|/\\D+)".toRegex()

// used in Variables object
val regexCheck1 = "([0-9]*?[a-zA-Z]+[0-9]*\\D*?)".toRegex()
val regexNotNum = "\\D+".toRegex()
val regexNum = "\\d".toRegex()