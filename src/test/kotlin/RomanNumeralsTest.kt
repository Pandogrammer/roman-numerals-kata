import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RomanNumeralsTest {

    private lateinit var romanConverter: RomanConverter

    private val knownNumbers = mapOf(
        1 to "I",
        5 to "V"
    )

    @Before
    fun setUp(){
        romanConverter = RomanConverter(knownNumbers)
    }
    @Test
    fun `numeros conocidos`(){
        assertEquals(knownNumbers, romanConverter.knownNumbers)
    }

}

class RomanConverter(val knownNumbers: Map<Int, String>) {
}