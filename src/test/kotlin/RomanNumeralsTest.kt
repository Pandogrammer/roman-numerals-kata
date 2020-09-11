import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
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

        assertTrue(uses <= 3)
        assertNotEquals("IIII", result)
    }

    @Test
    fun `Se pueden restar numeros poniendolos hacia la izquierda de otro numero`(){
        val result = romanConverter.convert(4)

        assertEquals("IV", result)
    }
}

class RomanConverter(val knownNumbers: Map<Int, String>) {
    private val usedNumbers = mutableMapOf<Int, Int>()

    fun convert(number: Int): String? {
        usedNumbers.clear()
        if (knownNumber(number))
            return convertKnownNumber(number)

        var result = convertBySum(number)
        if(result == null)
            result = convertBySub(number)
            if(result != null)
                return result
        return convertBySum(number)
    }

    private fun convertBySub(number: Int): String? {
        val nextNumber = knownNumbers.keys.first{ it > number }
        val reminder = nextNumber - number
        return convert(reminder) + convertKnownNumber(nextNumber)
    }

    private fun convertBySum(number: Int): String? {
        var sum = 0
        var result = ""
        while (sum < number) {
            val n = 1
            if(!canUse(n))
                return null
            result += convertKnownNumber(n)
            sum += n
        }
        return result
    }

    private fun convertIfCanUse(number: Int): String {
        return if(canUse(number)) convertKnownNumber(number) else ""
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
