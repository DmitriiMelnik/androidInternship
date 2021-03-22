package test

import CustomString
import org.junit.jupiter.api.Test
import kotlin.test.*

class CustomStringTest {

    private val stringValue: Array<Char> = arrayOf('h','e','l','l','o', ' ', 'w', 'o', 'r', 'l', 'd')
    private val shortStringValue1: Array<Char> = arrayOf('h','e','l','l','o',)
    private val shortStringValue2: Array<Char> = arrayOf('w', 'o', 'r', 'l', 'd')

    @Test
    fun getElementByIndexCheck() {
        val customString = CustomString(stringValue)
        stringValue.forEachIndexed {index, element ->
            assertEquals(element, customString[index])
        }
    }

    @Test
    fun concatCheck() {
        val customString1 = CustomString(shortStringValue1)
        val customString2 = CustomString(shortStringValue2)

        val concatString = CustomString.concat(customString1, customString2)

        shortStringValue1.forEachIndexed {index, element ->
            assertEquals(element, concatString[index])
        }
        shortStringValue2.forEachIndexed {index, element ->
            assertEquals(element, concatString[shortStringValue1.size + index])
        }
    }

    @Test
    fun plusCheck() {
        val customString1 = CustomString(shortStringValue1)
        val customString2 = CustomString(shortStringValue2)

        val concatString = customString1 + customString2

        shortStringValue1.forEachIndexed {index, element ->
            assertEquals(element, concatString[index])
        }
        shortStringValue2.forEachIndexed {index, element ->
            assertEquals(element, concatString[shortStringValue1.size + index])
        }
    }

    @Test
    fun plusCharCheck() {
        val customString1 = CustomString(shortStringValue1)

        val concatString = customString1 + 't'

        shortStringValue1.forEachIndexed {index, element ->
            assertEquals(element, concatString[index])
        }

        assertEquals(concatString[shortStringValue1.size], 't')
    }

    @Test
    fun plusIntCheck() {
        val customString1 = CustomString(shortStringValue1)

        val concatString = customString1 + 142

        shortStringValue1.forEachIndexed {index, element ->
            assertEquals(element, concatString[index])
        }

        assertEquals(concatString[shortStringValue1.size], '1')
        assertEquals(concatString[shortStringValue1.size+1], '4')
        assertEquals(concatString[shortStringValue1.size+2], '2')
    }


    @Test
    fun toStringCheck() {
        assertEquals(stringValue.joinToString(""), CustomString(stringValue).toString())
    }

    @Test
    fun toArrayCharCheck() {
        val customString = CustomString(stringValue).toArrayChar()
        customString.forEachIndexed { index, element ->
            assertEquals(element, customString[index])
        }
    }

    @Test
    fun containCheck() {
        val customString = CustomString(stringValue)
        assertTrue('h' in customString)
    }

    @Test
    fun containCheckFail() {
        val customString = CustomString(stringValue)
        assertFalse('k' in customString)
    }

    @Test
    fun sizeCheck() {
        val customString = CustomString(stringValue)
        assertEquals(stringValue.size, customString.size)
    }

    @Test
    fun withIndexCheck() {
        val customString = CustomString(stringValue)
        for ((index, char) in customString.withIndex()) {
            assertEquals(char, stringValue[index])
        }
    }

    @Test
    fun containStringCheck() {
        val customString = CustomString(stringValue)
        val shortString = CustomString(shortStringValue1)
        assertTrue(shortString in customString)
    }

    @Test
    fun containStringCheckFail() {
        val customString = CustomString(stringValue)
        val shortString = CustomString("test")
        assertFalse(shortString in customString)
    }

    @Test
    fun findChar() {
        val customString = CustomString(stringValue)
        val index = customString.find(stringValue[0])
        assertEquals(index, 0)
    }

    @Test
    fun findCharFail() {
        val customString = CustomString(stringValue)
        val index = customString.find('t')
        assertEquals(index, null)
    }

    @Test
    fun findString() {
        val customString = CustomString(stringValue)
        val shortString = CustomString(shortStringValue1)
        val index = customString.find(shortString)
        assertEquals(index, 0)
    }

    @Test
    fun findStringFail() {
        val customString = CustomString(stringValue)
        val index = customString.find('t')
        assertEquals(index, null)
    }

    @Test
    fun findAllChar() {
        val customString = CustomString(stringValue)
        val allIndex = customString.findAll('l')
        assertEquals(allIndex.size, 3)
        for ((index, char) in stringValue.withIndex()) {
            if(char == customString[index] && index in allIndex) {
                assert(true)
            }
        }
    }

    @Test
    fun findAllCharFail() {
        val customString = CustomString(stringValue)
        val allIndex = customString.findAll('t')
        assertEquals(allIndex.size, 0)
    }

    @Test
    fun findAllString() {
        val customString = CustomString(stringValue)
        val shortString = CustomString(shortStringValue2)
        val allIndex = customString.findAll(shortString)
        assertEquals(allIndex.size, 1)
        assertEquals(allIndex[0], 6)
    }

    @Test
    fun findAllStringFail() {
        val customString = CustomString(stringValue)
        val shortString = CustomString("test")
        val allIndex = customString.findAll(shortString)
        assertEquals(allIndex.size, 0)
    }

    @Test
    fun equalsString() {
        val customString1 = CustomString(stringValue)
        val customString2 = CustomString(stringValue)
        assertEquals(customString1, customString2)
    }

    @Test
    fun equalsStringFail() {
        val customString1 = CustomString(stringValue)
        val customString2 = CustomString(shortStringValue1)
        assertNotEquals(customString2, customString1)
    }

    @Test
    fun sliceCheck() {
        val customString1 = CustomString(stringValue).slice(0, 5)
        val customString2 = CustomString(shortStringValue1)
        assertEquals(customString1, customString2)
    }

    @Test
    fun sliceDefaultSizeCheck() {
        val customString1 = CustomString(stringValue).slice(6)
        val customString2 = CustomString(shortStringValue2)
        assertEquals(customString1, customString2)
    }

    @Test
    fun copyCheck() {
        val customString = CustomString(stringValue)
        val customStringCopy = customString.copy()
        assertNotSame(customString, customStringCopy)
        assertEquals(customString, customStringCopy)
    }

    @Test
    fun replaceCheck() {
        val customString = CustomString(stringValue).replace(CustomString('l'), CustomString('L'))
        for((index, char) in stringValue.withIndex()) {
            if (char == 'l') {
                assertEquals(customString[index],'L')
                break
            }
        }
    }

    @Test
    fun minusCheck() {
        val customString1 = CustomString(stringValue)
        val customString2 = CustomString(shortStringValue2)

        assertTrue(customString2 in customString1)
        val customString = customString1 - customString2
        assertFalse(customString2 in customString)
    }

    @Test
    fun intToCustomString() {
        val number = 1241
        val customString = CustomString(number)
        assertEquals(number.toString(), customString.toString())
    }

    @Test
    fun negativeIntToCustomString() {
        val number = -1241
        val customString = CustomString(number)
        assertEquals(number.toString(), customString.toString())
    }

    @Test
    fun floatToString() {
        val number = 4.5f
        val customString = CustomString(number)
        assertEquals(number.toString(), customString.toString())
    }

    @Test
    fun doubleToString() {
        val number = 4.5
        val customString = CustomString(number)
        assertEquals(number.toString(), customString.toString())
    }

    @Test
    fun negativeDoubleToString() {
        val number = -4.5
        val customString = CustomString(number)
        assertEquals(number.toString(), customString.toString())
    }

    @Test
    fun replaceAllCheck() {
        val customString = CustomString(stringValue).replaceAll('l', 'L')
        for((index, char) in stringValue.withIndex()) {
            if (char == 'l') {
                assertEquals(customString[index],'L')
            }
            else {
                assertEquals(customString[index], char)
            }
        }
    }

    @Test
    fun reverseCheck() {
        val customString = CustomString(stringValue)
        val reverseString = customString.reverse()
        for((index, char) in customString.withIndex()) {
            assertEquals(char, reverseString[reverseString.size - index -1])
        }
    }

    @Test
    fun toIntCheck() {
        val number = 1123
        val customString = CustomString(number)
        assertEquals(number, customString.toInt())
    }

    @Test
    fun toIntNegativeCheck() {
        val number = -1123
        val customString = CustomString(number)
        assertEquals(number, customString.toInt())
    }

    @Test
    fun toFloatCheck() {
        val number = 1123.5f
        val customString = CustomString(number)
        assertEquals(number, customString.toFloat())
    }

    @Test
    fun toFloatNegativeCheck() {
        val number = -1123.5f
        val customString = CustomString(number)
        assertEquals(number, customString.toFloat())
    }

    @Test
    fun toDoubleCheck() {
        //fail due to rounding during calculations
        val number = 1123.54
        val customString = CustomString(number)
        assertEquals(number, customString.toDouble())
    }

    @Test
    fun toDoubleNegativeCheck() {
        //fail due to rounding during calculations
        val number = -1123.54
        val customString = CustomString(number)
        assertEquals(number, customString.toDouble())
    }


}