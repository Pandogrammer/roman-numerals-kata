import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RomanNumeralsTest {

    private lateinit var romanConverter: RomanConverter

    private val knownNumbers = mapOf(
        1 to "I",
        5 to "V",
        10 to "X",
        50 to "L",
        100 to "C",
        500 to "D",
        1000 to "M"
    )

    private val invalidSubNumbers = listOf(
        5, 50, 500
    )

    @Before
    fun setUp() {
        romanConverter = RomanConverter(knownNumbers, invalidSubNumbers)
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

        assertTrue(uses <= 3)
        assertNotEquals("IIII", result)
    }

    @Test
    fun `Se pueden restar numeros poniendolos hacia la izquierda de otro numero`() {
        val result = romanConverter.convert(4)

        assertEquals("IV", result)
    }

    @Test
    fun `Prueba de nuevos numeros`() {
        val numbers = mapOf(9 to "IX", 8 to "VIII")

        numbers.keys.forEach {
            val result = romanConverter.convert(it)

            assertEquals(numbers[it], result)
        }
    }

    @Test
    fun `Los numeros que contienen un 5 no pueden usarse para restar`() {
        val result = romanConverter.convert(45)

        assertEquals("XLV", result)
    }
}

class RomanConverter(
    val knownNumbers: Map<Int, String>,
    val invalidSubNumbers: List<Int>
) {
    private val usedNumbers = mutableMapOf<Int, Int>()

    fun convert(number: Int): String? {
        usedNumbers.clear()
        if (knownNumber(number))
            return convertKnownNumber(number)

        var result = convertBySum(number)
        if (result == null)
            result = convertBySub(number)
        if (result != null)
            return result
        return convertBySum(number)
    }

    private fun convertBySub(number: Int): String? {
        val nextNumber = knownNumbers.keys.first { it > number }
        val previousNumber = knownNumbers.keys.last { it < nextNumber && validSubNumber(it) }
        val leftNumber = nextNumber - previousNumber
        val rightNumber = number - leftNumber
        return convert(previousNumber) + convert(nextNumber) + convert(rightNumber)
    }

    private fun validSubNumber(number: Int): Boolean {
        return !invalidSubNumbers.contains(number)
    }

    private fun convertBySum(number: Int): String? {
        var result = ""
        var reminder = number
        while (reminder > 0) {
            val n = knownNumbers.keys.last { it <= reminder }
            if (!canUse(n))
                return null
            result += convertKnownNumber(n)
            reminder = reminder - n
        }
        return result
    }

    private fun convertIfCanUse(number: Int): String {
        return if (canUse(number)) convertKnownNumber(number) else ""
    }

    private fun canUse(number: Int) = uses(number) < 3

    fun uses(knownNumber: Int): Int {
        return usedNumbers.getOrDefault(knownNumber, 0)
    }

    private fun knownNumber(number: Int) = knownNumbers.containsKey(number)

    private fun convertKnownNumber(number: Int): String {
        usedNumbers[number] = usedNumbers.getOrDefault(number, 0) + 1
        return knownNumbers.getValue(number)
    }

}
