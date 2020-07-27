package productsearch

import com.mongodb.DBCollection
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification
import org.testcontainers.containers.MongoDBContainer

@org.testcontainers.spock.Testcontainers
class ProductControllerSpec extends Specification implements ControllerUnitTest<ProductController> {
    MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10").withExposedPorts(27017)

        def "setup"() {
            mongoDBContainer.start()

        }

    void "test something"() {
        expect:"good job"
        true == true
    }
}