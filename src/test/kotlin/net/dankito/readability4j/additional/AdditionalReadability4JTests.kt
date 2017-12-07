package net.dankito.readability4j.additional

import net.dankito.readability4j.Readability4JTestBase
import org.junit.Test


open class AdditionalReadability4JTests : Readability4JTestBase() {


    @Test
    fun testBento1() {
        testPage("http://www.bento.de/haha/jamaika-aus-hier-sind-exklusiv-die-satirischen-fdp-wahlplakate-fuer-die-neuwahlen-1876078/", "bento-1")
    }

    @Test
    fun testFaz1() {
        testPage("http://www.faz.net/aktuell/wirtschaft/unternehmen/bmw-steckt-viel-geld-in-erforschung-der-batteriezelle-15308519.html", "faz-1")
    }

    @Test
    fun testSpiegel1() {
        testPage("http://www.spiegel.de/politik/deutschland/fdp-vize-wolfgang-kubicki-weist-gruenen-angriffe-scharf-zurueck-a-1180321.html", "spiegel-1")
    }

    @Test
    fun testSueddeutsche1() {
        testPage("http://www.sueddeutsche.de/wirtschaft/dienstleistungsgesellschaft-geiz-macht-arm-1.3764236", "sueddeutsche-1")
    }

    @Test
    fun testSueddeutscheParadisePapers() {
        testPage("https://projekte.sueddeutsche.de/paradisepapers/wirtschaft/carstensen-und-der-pharma-milliardaer-e624335", "sueddeutsche-paradise-papers")
    }

    @Test
    fun testZeit1() {
        testPage("http://www.zeit.de/2017/48/simbabwes-jugend-robert-mugabe-ruecktritt", "zeit-1")
    }

    protected open fun testPage(url: String, pageName: String) {
        testPage(url, "additional-test-pages", pageName)
    }


}