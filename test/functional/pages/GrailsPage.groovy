package pages

import geb.Page


abstract  class GrailsPage extends Page {
    @Override
    def <T> T waitFor(String s, Closure<T> closure) {


    }

    @Override
    def <T> T waitFor(Map map, String s, Closure<T> closure) {
        return null
    }

    @Override
    def <T> T waitFor(Closure<T> closure) {
        return null
    }

    @Override
    def <T> T waitFor(Map map, Closure<T> closure) {
        return null
    }

    @Override
    def <T> T waitFor(Double aDouble, Closure<T> closure) {
        return null
    }

    @Override
    def <T> T waitFor(Map map, Double aDouble, Closure<T> closure) {
        return null
    }

    @Override
    def <T> T waitFor(Double aDouble, Double aDouble1, Closure<T> closure) {
        return null
    }

    @Override
    def <T> T waitFor(Map map, Double aDouble, Double aDouble1, Closure<T> closure) {
        return null
    }
}
