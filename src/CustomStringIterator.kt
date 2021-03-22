
class CustomStringIterator(private val string: CustomString) : Iterator<Char>{
    var currentIndex = 0

    override fun hasNext(): Boolean = currentIndex < string.size

    override fun next(): Char {
        val value = string[currentIndex]
        currentIndex++
        return value
    }
}