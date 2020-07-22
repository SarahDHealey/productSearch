package productSearch

import grails.testing.gorm.DomainUnitTest
import grails.test.mongodb.MongoSpec
import com.github.fakemongo.Fongo
import com.mongodb.MongoClient
import spock.lang.Ignore

//To write unit tests with MongoDB and Spock, you can simply extend from grails.test.mongodb.MongoSpec.

/*Override the createMongoClient method of the MongoSpec base class. Use something such as Fongo.
Fongo is an in-memory java implementation of MongoDB.
It intercepts calls to the standard mongo-java-driver for finds, updates, inserts, removes and other methods.
The primary use is for lightweight unit testing where you don't want to spin up a mongod process.
 */

@Ignore
@SuppressWarnings(['MethodName'])
class ProductSpec extends MongoSpec implements DomainUnitTest<Product> {

    @Override
    MongoClient createMongoClient() {
        new Fongo(getClass().name).mongo
    }

    void 'a negative value is not a valid price'() {
        given:
        domain.price = -2.0d

        expect:
        !domain.validate(['price'])
    }

    void 'a blank name is not save'() {
        given:
        domain.name = ''

        expect:
        !domain.validate(['name'])
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

