import kotlin.math.abs
import kotlin.math.pow

class CustomString {

    constructor() {
        value = arrayOf()
    }

    constructor(_value: Char) {
        value = arrayOf(_value)
    }

    constructor(_value: Array<Char>) {
        value = _value
    }

    constructor(_value: String) {
        value = _value.toCharArray().toTypedArray()
    }

    constructor(_value: Int) {
        val isPositive = _value >= 0
        var intValue = abs(_value)
        var reverseValue: Array<Char> = arrayOf()
        while(intValue >= 10) {
            reverseValue += ((intValue % 10) + '0'.toInt()).toChar()
            intValue /= 10
        }
        reverseValue += (intValue + '0'.toInt()).toChar()

        value = if (isPositive) arrayOf() else arrayOf('-')
        for(i in reverseValue.size - 1 downTo 0) {
            value += reverseValue[i]
        }
    }

    constructor(_value: Float) {
        val integer = _value.toInt()
        var decimal = abs(_value - integer)

        var decimalValue: Array<Char> = arrayOf()
        while(decimal > 0 && decimal < 1) {
            decimal *= 10
            decimalValue += (decimal.toInt() + '0'.toInt()).toChar()
            decimal -= decimal.toInt()
        }

        value = CustomString(_value.toInt()).toArrayChar() + '.' + decimalValue
    }

    constructor(_value: Double) {
        val integer = _value.toInt()
        var decimal = abs(_value - integer)

        var decimalValue: Array<Char> = arrayOf()
        while(decimal > 0 && decimal < 1) {
            decimal *= 10
            decimalValue += (decimal.toInt() + '0'.toInt()).toChar()
            decimal -= decimal.toInt()
        }

        value = CustomString(_value.toInt()).toArrayChar() + '.' + decimalValue
    }

    private var value: Array<Char>

    val size: Int
        get(): Int = value.size

    companion object {
        fun concat(customString1: CustomString, customString2: CustomString): CustomString {
            return CustomString(customString1.toArrayChar() + customString2.toArrayChar())
        }

        fun equals(string1: CustomString, string2: CustomString): Boolean {
            if (string1.size == string2.size) {
                for ((index, item) in string1.withIndex()) {
                    if (item != string2[index]) {
                        return false
                    }
                }
                return true
            }
            return false
        }

        fun copy(customString: CustomString) : CustomString = CustomString(customString.toArrayChar().clone())

        fun slice(string: CustomString, startIndex: Int, count: Int?): CustomString {
            var sliceArray: Array<Char> = arrayOf()
            val endIndex = if (count == null) string.size else startIndex+count
            for (index in startIndex until endIndex) {
                sliceArray += string[index]
            }
            return CustomString(sliceArray)
        }

        fun find(string: CustomString, findString: CustomString): Int? {
            for ((index1) in string.withIndex()) {
                var isEquals = true
                for ((index2, item2) in findString.withIndex()) {
                    if (string.size < index1 + index2 || string[index1 + index2] != item2) {
                        isEquals = false
                        break
                    }
                }
                if (isEquals) {
                    return index1
                }
            }
            return null
        }

        fun findAll(string: CustomString, findString: CustomString): Array<Int> {
            var list: Array<Int> = arrayOf()

            for ((index1) in string.withIndex()) {
                var isEquals = true
                for ((index2, item2) in findString.withIndex()) {
                    if (string.size < index1 + index2 || string[index1 + index2] != item2) {
                        isEquals = false
                        break
                    }
                }
                if (isEquals) {
                    list += index1
                }
            }

            return list
        }

        fun replace(string: CustomString, findString: CustomString, replaceString: CustomString): CustomString {
            val index = string.find(findString)
            if (index != null) {
                return string.slice(0, index) + replaceString + string.slice(index + findString.size)
            }
            return string.copy()
        }

        fun replaceAll(string: CustomString, findString: CustomString, replaceString: CustomString): CustomString {
            val allIndex = string.findAll(findString)
            allIndex.reverse()
            var newString: CustomString = string.copy()
            for (index in allIndex) {
                val otherString = newString.slice(index+1)
                newString = newString.slice(0, index) + replaceString + otherString
            }
            return newString
        }

        fun reverse(string: CustomString): CustomString {
            var newValue : Array<Char> = arrayOf()
            for(i in string.size - 1 downTo 0) {
                newValue += string[i]
            }
            return CustomString(newValue)
        }

        fun toInt(string: CustomString): Int? {
            var number: Int? = null
            var isNegative = false
            for((index, char) in string.withIndex()) {
                val oneNumber = (char.toInt() - '0'.toInt())
                if(oneNumber in 0..9) {
                    if (number == null) {
                        number = oneNumber
                        isNegative = index-1 >= 0 && string[index-1] == '-'
                    }else {
                        number = number * 10 + oneNumber
                    }
                } else if (number != null) {
                    break
                }
            }
            return if(isNegative && number !== null) -number else number
        }

        fun toFloat(string: CustomString): Float? {
            var number: Float? = null
            var isNegative = false
            var isDecimal = false
            var countDecimal = 0

            for((index, char) in string.withIndex()) {
                val oneNumber = (char.toInt() - '0'.toInt())
                if(oneNumber in 0..9) {
                    if (number == null) {
                        number = oneNumber.toFloat()
                        isNegative = index-1 >= 0 && string[index-1] == '-'
                    }else {
                        number = number * 10 + oneNumber
                    }

                    if(isDecimal) {
                        countDecimal++
                    }
                } else if (char == '.') {
                    isDecimal = true
                } else if (number != null) {
                    break
                }
            }

            number = if (number !== null) number * ((10f).pow(-countDecimal)) else number
            return if(isNegative && number !== null) -number else number
        }

        fun toDouble(string: CustomString): Double? {
            var number: Double? = null
            var isNegative = false
            var isDecimal = false
            var countDecimal = 0

            for((index, char) in string.withIndex()) {
                val oneNumber = (char.toInt() - '0'.toInt())
                if(oneNumber in 0..9) {
                    if (number == null) {
                        number = oneNumber.toDouble()
                        isNegative = index-1 >= 0 && string[index-1] == '-'
                    }else {
                        number = number * 10 + oneNumber
                    }

                    if(isDecimal) {
                        countDecimal++
                    }
                } else if (char == '.') {
                    isDecimal = true
                } else if (number != null) {
                    break
                }
            }

            number = if (number !== null) number * ((10f).pow(-countDecimal)) else number
            return if(isNegative && number !== null) -number else number
        }
    }

    fun copy(): CustomString = copy(this)

    override fun toString(): String = value.joinToString("")

    fun toArrayChar(): Array<Char> = value

    operator fun get(index: Int): Char = value[index]

    operator fun plus(customString: CustomString): CustomString  = concat(this, customString)
    operator fun plus(number: Int): CustomString  = concat(this, CustomString(number))
    operator fun plus(char: Char): CustomString  = concat(this, CustomString(char))

    fun slice(startIndex: Int, count: Int? = null): CustomString = slice(this, startIndex, count)

    fun replace(findString: CustomString, replaceString: CustomString): CustomString {
        return replace(this, findString, replaceString)
    }

    fun replaceAll(findString: CustomString, replaceString: CustomString): CustomString {
        return replaceAll(this, findString, replaceString)
    }
    fun replaceAll(findString: Char, replaceString: Char): CustomString {
        return replaceAll(CustomString(findString), CustomString(replaceString))
    }
    fun replaceAll(findString: Int, replaceString: Int): CustomString {
        return replaceAll(CustomString(findString), CustomString(replaceString))
    }
    fun replaceAll(findString: Float, replaceString: Float): CustomString {
        return replaceAll(CustomString(findString), CustomString(replaceString))
    }
    fun replaceAll(findString: Double, replaceString: Double): CustomString {
        return replaceAll(CustomString(findString), CustomString(replaceString))
    }


    operator fun minus(customString: CustomString): CustomString = replace(customString, CustomString())
    operator fun minus(char: Char): CustomString = replace(CustomString(char), CustomString())
    operator fun minus(number: Int): CustomString = replace(CustomString(number), CustomString())
    operator fun minus(number: Float): CustomString = replace(CustomString(number), CustomString())
    operator fun minus(number: Double): CustomString = replace(CustomString(number), CustomString())

    operator fun iterator(): Iterator<Char> = CustomStringIterator(this)

    fun withIndex(): Iterator<CustomStringWithIndex> = CustomStringWithIndexIterator(this)

    fun find(string: CustomString): Int? = find(this, string)
    fun find(char: Char): Int? = find(CustomString(char))
    fun find(number: Int): Int? = find(CustomString(number))
    fun find(number: Float): Int? = find(CustomString(number))
    fun find(number: Double): Int? = find(CustomString(number))

    fun findAll(string: CustomString): Array<Int> = findAll(this, string)
    fun findAll(char: Char): Array<Int> = findAll(CustomString(char))

    operator fun contains(char: Char): Boolean = find(char) != null
    operator fun contains(string: CustomString): Boolean = find(string) != null


    override fun equals (other: Any?): Boolean {
        if (other is CustomString) {
            return equals(this, other)
        }
        if (other is Int) {
            return equals(this, CustomString(other))
        }
        if (other is Float) {
            return equals(this, CustomString(other))
        }
        if (other is Double) {
            return equals(this, CustomString(other))
        }
        if (other is Char) {
            return equals(this, CustomString(other))
        }
        return false
    }

    override fun hashCode(): Int = value.contentHashCode()

    fun reverse(): CustomString = reverse(this)

    fun toInt(): Int? = toInt(this)

    fun toFloat(): Float? = toFloat(this)

    fun toDouble(): Double? = toDouble(this)
}