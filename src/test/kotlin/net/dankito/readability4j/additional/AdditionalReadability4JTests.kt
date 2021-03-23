package net.dankito.readability4j.additional

import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import net.dankito.readability4j.Article
import net.dankito.readability4j.Readability4JTestBase
import org.junit.Test


open class AdditionalReadability4JTests : Readability4JTestBase() {


    @Test
    fun fixImgElementsWithoutSrcAttributes() {
        // see https://github.com/dankito/Readability4J/issues/4
        testPage("https://www.msn.com/en-us/news/technology/facebook-says-attackers-stole-details-from-29-mln-users/ar-BBOiiJa", "fix-img-without-src")
    }


    @Test
    fun testAmpImages() {
        testPage("https://www.heise.de/tipps-tricks/Daten-in-der-Cloud-sicher-verschluesseln-3952178.html", "amp-img")
    }

    @Test
    fun testAndroidDevelopersBlog() {
        testPage("http://feeds.feedburner.com/blogspot/hsDu", "android-developers-blog")
    }

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
        val article = testPage("https://projekte.sueddeutsche.de/paradisepapers/wirtschaft/carstensen-und-der-pharma-milliardaer-e624335/", "sueddeutsche-paradise-papers")

        assertEquals("Paradise Papers: Carstensen und der Pharma-Milliardär", article.title)
        assertStartsWith("Hegen und Pflegen Der CDU-Politiker Peter Harry Carstensen und der Pharma-Unternehmer Frederik Paulsen sind eng miteinander verbandelt. Möglicherweise zu eng Die Eröffnung des neuen, gut 13 Millionen Euro teuren Museums", article.textContent)
        assertContains("Der frühere Kieler Regierungschef bestreitet den Vorgang im Grunde nicht, nennt aber keine Summen: \"So weit ich", article.textContent)
        assertContains("Das trifft zwar auf sein Engagement in der Gregor-Mendel-Stiftung zu, die sich um Pflanzenzüchtungen kümmert und deren Kuratoriums-Vorsitzender er ist", article.textContent)
        assertContains("Den Dokumenten nach hat Carstensen den Posten kaum ein halbes Jahr nach seinem Rücktritt als Ministerpräsident 2012 angenommen, er selbst bezeichnet sich als \"nichtexekutiver Direktor\" des Insel-Museums", article.textContent)
        assertContains("Alles also nur ein Dienst unter Freunden, nichts dabei? Timo Lange von der Antikorruptionsorganisation Lobbycontrol sieht das ganz anders: \"Das ist schon ein sehr interessanter Fall", article.textContent)
        assertContains("Immer, wenn Politiker die Seiten wechseln, ob es den Altkanzler Gerhard Schröder (SPD) in die russische Energiewirtschaft zieht, den Kanzleramtsminister Ronald Pofalla (CDU) zur Deutschen Bahn", article.textContent)
        assertContains("Carstensens Verbindung zu Paulsen, sagt Experte Lange von Lobbycontrol, sei genau so ein Fall, der heute unter die Kieler Karenzzeitregel fallen würde. Lange:", article.textContent)
        assertContains("Frederik Paulsen lässt jeglichen Verdacht zurückweisen, er würde seinem Freund Carstensen irgendeine \"Entlohnung für politische Gefälligkeiten\" gewähren.", article.textContent)
        assertContains("Er sei bei dem Weingut wegen seines \"Sachverstands in landwirtschaftlichen Fragen\" beratend tätig. Im Übrigen sei Georgien ein interessantes Land.", article.textContent)
    }

    @Test
    fun testZeit1() {
        testPage("http://www.zeit.de/2017/48/simbabwes-jugend-robert-mugabe-ruecktritt", "zeit-1")
    }


    protected open fun testPage(url: String, pageName: String): Article {
        return testPage(url, "additional-test-pages", pageName)
    }


    protected open fun assertStartsWith(expected: String, actual: String?) {
        if(actual?.startsWith(expected) != false) {
            fail(String.format("Expected \n[%s]\n at start of \n[%s]\n", expected, actual))
        }
    }

    protected open fun assertContains(expected: String, actual: String?) {
        if(actual?.contains(expected) != true) {
            fail(String.format("Expected \n[%s]\n in \n[%s]\n", expected, actual))
        }
    }


}