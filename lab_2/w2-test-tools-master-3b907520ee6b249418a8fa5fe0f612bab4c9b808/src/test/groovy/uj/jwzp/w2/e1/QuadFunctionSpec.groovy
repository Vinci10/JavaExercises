package uj.jwzp.w2.e1

import spock.lang.Specification

class QuadFunctionSpec extends Specification {

    def "should return no values"() {
        given:
        def uut = new QuadFuction()
        when:
        def result = uut.quadFunction(1,2,3)
        then:
        result.size() == 0
    }

    def "should return one value"() {
        given:
        def uut = new QuadFuction()
        when:
        def result = uut.quadFunction(2,4,2)
        then:
        result.size() == 1
        result.get(0) == -1.0
    }

    def "should return two value"() {
        given:
        def uut = new QuadFuction()
        when:
        def result = uut.quadFunction(2,2,-12)
        then:
        result.size() == 2
        result.get(0) == -25.5
        result.get(1) == 24.5
    }

}
