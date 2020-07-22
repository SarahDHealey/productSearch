package productSearch

import grails.testing.web.controllers.ControllerUnitTest
import com.github.fakemongo.Fongo
import spock.lang.Ignore
import com.mongodb.MongoClient
import grails.test.mongodb.MongoSpec


@Ignore
@SuppressWarnings('MethodName')
class ProductControllerSpec extends MongoSpec
        implements ControllerUnitTest<ProductController> {

    @Override
    MongoClient createMongoClient() {
        new Fongo(getClass().name).mongo
    }
    void setup() {
        Product.saveAll(
                new Product(name: 'Apple', price: 2.0),
                new Product(name: 'Orange', price: 3.0),
                new Product(name: 'Banana', price: 1.0),
                new Product(name: 'Cake', price: 4.0)
        )
    }
    void 'test the search action finds results'() {
        when: 'A query is executed that finds results'
        controller.search('pp', 10)

        then: 'The response is correct'
        response.json.size() == 1
        response.json[0].name == 'Apple'
    }
}