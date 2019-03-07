package net.dankito.readability4j.util

import net.dankito.readability4j.Readability4J
import net.dankito.readability4j.Readability4JTest
import net.dankito.readability4j.extended.Readability4JExtended
import net.dankito.readability4j.model.ReadabilityOptions
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*


fun main(args: Array<String>) {
    TestDataReParser().reParseTestData()
}


class TestDataReParser : TestDataGeneratorBase() {

    fun reParseTestData() {
        reParseMozillasTestData()

        reParseAdditionalTestData()
    }

    private fun reParseMozillasTestData() {
        Arrays.asList(

                "001", "002", "ars-1", "base-url", "basic-tags-cleaning", "bbc-1", "blogger",
                "breitbart", "bug-1255978", "buzzfeed-1", "clean-links", "cnet", "cnet-svg-classes", "cnn",
                "comment-inside-script-parsing", "daringfireball-1", "ehow-1", "ehow-2",
                "embedded-videos", "gmw", "heise", "herald-sun-1", "hukumusume", "iab-1", "ietf-1",
                "keep-images", "la-nacion", "lemonde-1", "liberation-1", "lifehacker-post-comment-load",
                "lifehacker-working", "links-in-tables", "lwn-1", "medium-1", "medium-2", "medium-3",
                "missing-paragraphs", "mozilla-1", "mozilla-2", "msn", "normalize-spaces",
                "nytimes-1", "nytimes-2", "pixnet", "qq", "remove-extra-brs",
                "remove-extra-paragraphs", "remove-script-tags", "reordering-paragraphs",
                "replace-brs", "replace-font-tags",
                "rtl-1", "rtl-2", "rtl-3", "rtl-4",
                "salon-1", "simplyfound-1", "social-buttons", "style-tags-removal", "svg-parsing",
                "table-style-attributes", "telegraph", "title-and-h1-discrepancy", "tmz-1", "tumblr",
                "wapo-1", "wapo-2", "webmd-1", "webmd-2", "wikia",
                "wikipedia", "wordpress",
                "yahoo-1", "yahoo-2", "yahoo-3", "yahoo-4", "youth"

        ).forEach {
            reParseMozillasTestData(it)
        }
    }

    private fun reParseMozillasTestData(testCaseName: String) {
        reParseTestData(Readability4JTest.ReadabilityFakeTestUrl, "test-pages", testCaseName)
    }

    private fun reParseAdditionalTestData() {
        reParseAdditionalTestData("https://www.msn.com/en-us/news/technology/facebook-says-attackers-stole-details-from-29-mln-users/ar-BBOiiJa", "fix-img-without-src")

        reParseAdditionalTestData("https://www.heise.de/tipps-tricks/Daten-in-der-Cloud-sicher-verschluesseln-3952178.html", "amp-img")
        reParseAdditionalTestData("http://www.bento.de/haha/jamaika-aus-hier-sind-exklusiv-die-satirischen-fdp-wahlplakate-fuer-die-neuwahlen-1876078/", "bento-1")
        reParseAdditionalTestData("http://www.faz.net/aktuell/wirtschaft/unternehmen/bmw-steckt-viel-geld-in-erforschung-der-batteriezelle-15308519.html", "faz-1")
        reParseAdditionalTestData("http://www.faz.net/aktuell/politik/spd-sonderparteitag-begeisternde-rede-von-andrea-nahles-15410318-p2.html", "faz-2")
        reParseAdditionalTestData("http://www.shgjj.com/html/zyxw/101770.html", "shgjj-1")
        reParseAdditionalTestData("http://www.spiegel.de/politik/deutschland/fdp-vize-wolfgang-kubicki-weist-gruenen-angriffe-scharf-zurueck-a-1180321.html", "spiegel-1")
        reParseAdditionalTestData("http://www.sueddeutsche.de/wirtschaft/dienstleistungsgesellschaft-geiz-macht-arm-1.3764236", "sueddeutsche-1")
        reParseAdditionalTestData("https://projekte.sueddeutsche.de/paradisepapers/wirtschaft/carstensen-und-der-pharma-milliardaer-e624335/", "sueddeutsche-paradise-papers")
        reParseAdditionalTestData("http://www.zeit.de/2017/48/simbabwes-jugend-robert-mugabe-ruecktritt", "zeit-1")
        reParseAdditionalTestData("https://zhuanlan.zhihu.com/p/22049205", "zhihu-1")
    }

    private fun reParseAdditionalTestData(url: String, testCaseName: String) {
        reParseTestData(url, "additional-test-pages", testCaseName)
    }

    private fun reParseTestData(url: String, testFolderName: String, testCaseName: String) {
        val sourceHtml = getFileContentFromResource(testFolderName, testCaseName, "source.html")

        val readability = Readability4J(url, sourceHtml,
                ReadabilityOptions(additionalClassesToPreserve = Arrays.asList("caption")))
        val article = readability.parse()

        val readabilityExtended = Readability4JExtended(url, sourceHtml,
                ReadabilityOptions(additionalClassesToPreserve = Arrays.asList("caption")))
        val articleExtended = readabilityExtended.parse()

        writeTestData(sourceHtml, article, articleExtended, testFolderName, testCaseName)
    }


    private fun getFileContentFromResource(testPageFolderName: String, pageName: String, resourceFilename: String): String {
        val url = this.javaClass.classLoader.getResource("$testPageFolderName/$pageName/$resourceFilename")
        val file = File(url.toURI())

        val reader = FileReader(file) // TODO: set encoding
        val fileContent = BufferedReader(reader).readText()

        reader.close()

        return fileContent
    }

}