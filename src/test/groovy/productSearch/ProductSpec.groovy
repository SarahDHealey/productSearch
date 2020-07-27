package productsearch

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Unroll

//To write unit tests with MongoDB and Spock, you can simply extend from grails.test.mongodb.MongoSpec.

/*Override the createMongoClient method of the MongoSpec base class. Use something such as Fongo.
Fongo is an in-memory java implementation of MongoDB.
It intercepts calls to the standard mongo-java-driver for finds, updates, inserts, removes and other methods.
The primary use is for lightweight unit testing where you don't want to spin up a mongod process.
 */

class ProductSpec extends Specification implements DomainUnitTest<Product> {
    void 'test name cannot be null'() {
        when:
        domain.name = null

        then:
        !domain.validate(['name'])
        domain.errors['name'].code == 'nullable'
    }
    void 'test name cannot be blank'() {
        when:
        domain.name = ''

        then:
        !domain.validate(['name'])
    }

    void 'test name can have a maximum of 255 characters'() {
        when: 'for a string of 256 characters'
        String str = 'a' * 256
        domain.name = str

        then: 'name validation fails'

        !domain.validate(['name'])
        domain.errors['name'].code == 'maxSize.exceeded'

        when: 'for a string of 256 characters'
        str = 'a' * 255
        domain.name = str

        then: 'name validation passes'
        domain.validate(['name'])
    }
        @Unroll('validate on a product with price #productPrice should have returned #shouldBeValid')
        void 'test price validation'() {
        expect:
        new Product(price: productPrice).validate(['price']) == shouldBeValid

        where:
        productPrice | shouldBeValid
        -2           | false
        -1           | false
        0            | true
        1            | true
        100          | true
    }

        void 'A valid domain is saved'() {
        given:
        domain.name = 'Banana'
        domain.price = 2.15d

        when:
        domain.save()

        then:
        Product.count() == old(Product.count()) + 1
    }

}


//
//@SuppressWarnings(['MethodName', 'DuplicateNumberLiteral', 'TrailingWhitespace'])
//class ProductSpec extends MongoSpec implements EmbeddedMongoClient {
//
//    @Override
//    MongoClient createMongoClient() {
//        new Fongo(getClass().name).mongo
//    }
//
//    void 'a negative value is not a valid price'() {
//        given:
//        domain.price = -2.0d
//
//        expect:
//        !domain.validate(['price'])
//    }
//
//    void 'a blank name is not save'() {
//        given:
//        domain.name = ''
//
//        expect:
//        !domain.validate(['name'])
//    }
//
//    void 'A valid domain is saved'() {
//        given:
//        domain.name = 'Banana'
//        domain.price = 2.15d
//
//        when:
//        domain.save()
//
//        then:
//        Product.count() == old(Product.count()) + 1
//    }
//}
//
