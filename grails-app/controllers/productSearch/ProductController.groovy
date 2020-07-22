package productSearch


import grails.rest.RestfulController
import groovy.transform.CompileStatic
import productSearch.Product

class ProductController extends RestfulController {
    static responseFormats = ['json', 'xml']
    ProductController() {
        super(Product)
    }

    def search(String queryParam, Integer max) {
        if(queryParam) {
            def query = Product.where {
                name ==~ ~/$queryParam/
            }
            respond query.list(max: Math.min(max ?: 10, 100))
        }
        else {
            respond([])
        }
    }
}
