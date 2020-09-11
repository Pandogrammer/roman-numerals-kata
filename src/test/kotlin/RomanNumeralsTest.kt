import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class RomanNumeralsTest {

    private lateinit var romanConverter: RomanConverter

    private val knownNumbers = mapOf(
        1 to "I",
        5 to "V"
    )

    @Before
    fun setUp() {
        romanConverter = RomanConverter(knownNumbers)
    }

    @Test
    fun `Numeros conocidos`() {
        assertEquals(knownNumbers, romanConverter.knownNumbers)
    }

    @Test
    fun `Si es un numero conocido se imprime directamente`() {
        val numbers = knownNumbers.keys

        numbers.forEach {
            val result = romanConverter.convert(it)

            assertEquals(knownNumbers[it], result)
        }
    }

    @Test
    fun `Puedo generar numeros desconocidos sumando numeros conocidos`() {
        val numbers = mapOf(2 to "II", 3 to "III")

        numbers.keys.forEach {
            val result = romanConverter.convert(it)

            assertEquals(numbers[it], result)
        }
    }

    @Test
    fun `Un numero puede estar repetido hasta tres veces`() {
        val result = romanConverter.convert(4)

        val uses = romanConverter.uses(1)

        assertEquals(3, uses)
        assertNotEquals("IIII", result)
    }
}

class RomanConverter(val knownNumbers: Map<Int, String>) {
    private val usedNumbers = mutableMapOf<Int, Int>()

    fun convert(number: Int): String? {
        usedNumbers.clear()
        if (knownNumber(number))
            return convertKnownNumber(number)

        var sum = 0
        var result = ""
        while (sum < number) {
            val n = 1
            result += convertIfCanUse(n)
            sum += n
        }

        return result
    }

    private fun convertIfCanUse(number: Int): String {
        return if(uses(number) < 3) convertKnownNumber(number) else ""
    }

    fun uses(knownNumber: Int): Int {
        return usedNumbers.getOrDefault(knownNumber, 0)
    }

    private fun knownNumber(number: Int) = knownNumbers.containsKey(number)
    private fun convertKnownNumber(number: Int): String {
        usedNumbers[number] = usedNumbers.getOrDefault(number, 0) + 1
        return knownNumbers.getValue(number)
    }

}
