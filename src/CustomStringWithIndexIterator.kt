data class CustomStringWithIndex(val index: Int, val char: Char)

class CustomStringWithIndexIterator(private val string: CustomString) : Iterator<CustomStringWithIndex>{
    var currentIndex = 0

    override fun hasNext(): Boolean = currentIndex < string.size

    override fun next(): CustomStringWithIndex {
        val value = CustomStringWithIndex(currentIndex, string[currentIndex])
        currentIndex++
        return value
    }
}