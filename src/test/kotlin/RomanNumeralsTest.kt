import junit.framework.Assert.assertEquals
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
    fun `numeros conocidos`() {
        assertEquals(knownNumbers, romanConverter.knownNumbers)
    }

    @Test
    fun `si es un numero conocido se imprime directamente`() {
        val numbers = knownNumbers.keys

        numbers.forEach {
            val result = romanConverter.convert(it)

            assertEquals(knownNumbers[it], result)
        }
    }

    @Test
    fun `puedo generar numeros desconocidos sumando numeros conocidos`() {
        val numbers = mapOf(2 to "II", 3 to "III")

        numbers.keys.forEach {
            val result = romanConverter.convert(it)

            assertEquals(numbers[it], result)
        }
    }
}

class RomanConverter(val knownNumbers: Map<Int, String>) {
    fun convert(number: Int): String? {
        if (knownNumber(number))
            return convertKnownNumber(number)

        var sum = 0
        var result = ""
        while(sum < number){
            val n = 1
            result += convertKnownNumber(n)
            sum += n
        }

        return result
    }

    private fun convertKnownNumber(number: Int) = knownNumbers.getValue(number)

    private fun knownNumber(number: Int) = knownNumbers.containsKey(number)

}
