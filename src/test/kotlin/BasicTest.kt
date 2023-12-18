import org.elasticsearch.test.ESTestCase
import org.junit.Assert
import org.junit.Test

class BasicTest: ESTestCase() {
    @Test
    fun testBasic() {
        logger.info("test")
        Assert.assertEquals(1, 1)
    }
}
