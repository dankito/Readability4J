package net.dankito.readability4j

import org.junit.Test

open class Readability4JTest : Readability4JTestBase() {

    companion object {
        const val ReadabilityFakeTestUrl = "http://fakehost/test/page.html"
    }


    @Test
    fun test001() {
        testPage("001")
    }

    @Test
    fun test002() {
        testPage("002")
    }

    @Test
    fun testArs1() {
        testPage("ars-1")
    }

    @Test
    fun testBaseUrl() {
        testPage("base-url")
    }

    @Test
    fun testBasicTagsCleaning() {
        testPage("basic-tags-cleaning")
    }

    @Test
    fun testBBC1() {
        testPage("bbc-1")
    }

    @Test
    fun testBlogger() {
        testPage("blogger")
    }

    @Test
    fun testBreitbart() {
        testPage("breitbart")
    }

    @Test
    fun testBug1255978() {
        testPage("bug-1255978")
    }

    @Test
    fun testBuzzfeed1() {
        testPage("buzzfeed-1")
    }

    @Test
    fun testCleanLinks() {
        testPage("clean-links")
    }

    @Test
    fun testCnet() {
        testPage("cnet")
    }

    @Test
    fun testCNN() {
        testPage("cnn")
    }

    @Test
    fun testCommentInsideScriptParsing() {
        testPage("comment-inside-script-parsing")
    }

    @Test
    fun testDaringFireball1() {
        testPage("daringfireball-1")
    }

    @Test
    fun testEHow1() {
        testPage("ehow-1")
    }

    @Test
    fun testEHow2() {
        testPage("ehow-2")
    }

    @Test
    fun testEmbeddedVideos() {
        testPage("embedded-videos")
    }

    @Test
    fun testGmw() {
        testPage("gmw")
    }

    @Test
    fun testHeise() {
        testPage("heise")
    }

    @Test
    fun testHeraldSun1() {
        testPage("herald-sun-1")
    }

    @Test
    fun testIab1() {
        testPage("iab-1")
    }

    @Test
    fun testIETF1() {
        testPage("ietf-1")
    }

    @Test
    fun testKeepImages() {
        testPage("keep-images")
    }

    @Test
    fun testLeMonde1() {
        testPage("lemonde-1")
    }

    @Test
    fun testLiberation1() {
        testPage("liberation-1")
    }

    @Test
    fun testLifehackerPostCommentLoad() {
        testPage("lifehacker-post-comment-load")
    }

    @Test
    fun testLifehackerWorking() {
        testPage("lifehacker-working")
    }

    @Test
    fun testLinksInTables() {
        testPage("links-in-tables")
    }

    @Test
    fun testLwn1() {
        testPage("lwn-1")
    }

    @Test
    fun testMedium1() {
        testPage("medium-1")
    }

    @Test
    fun testMedium2() {
        testPage("medium-2")
    }

    @Test
    fun testMedium3() {
        testPage("medium-3")
    }

    @Test
    fun testMissingParagraphs() {
        testPage("missing-paragraphs")
    }

    @Test
    fun testMozilla1() {
        testPage("mozilla-1")
    }

    @Test
    fun testMozilla2() {
        testPage("mozilla-2")
    }

    @Test
    fun testMsn() {
        testPage("msn")
    }

    @Test
    fun testNormalizeSpaces() {
        testPage("normalize-spaces")
    }

    @Test
    fun testNYTimes1() {
        testPage("nytimes-1")
    }

    @Test
    fun testNYTimes2() {
        testPage("nytimes-2")
    }

    @Test
    fun testPixnet() {
        testPage("pixnet")
    }

    @Test
    fun testQQ() {
        testPage("qq")
    }

    @Test
    fun testRemoveExtraBrs() {
        testPage("remove-extra-brs")
    }

    @Test
    fun testRemoveExtraParagraphs() {
        testPage("remove-extra-paragraphs")
    }

    @Test
    fun testRemoveScriptTags() {
        testPage("remove-script-tags")
    }

    @Test
    fun testReorderingParagraphs() {
        testPage("reordering-paragraphs")
    }

    @Test
    fun testReplaceBrs() {
        testPage("replace-brs")
    }

    @Test
    fun testReplaceFontTags() {
        testPage("replace-font-tags")
    }

    @Test
    fun testRTL1() {
        testPage("rtl-1")
    }

    @Test
    fun testRTL2() {
        testPage("rtl-2")
    }

    @Test
    fun testRTL3() {
        testPage("rtl-3")
    }

    @Test
    fun testRTL4() {
        testPage("rtl-4")
    }

    @Test
    fun testSalon1() {
        testPage("salon-1")
    }

    @Test
    fun testSimplyFound1() {
        testPage("simplyfound-1")
    }

    @Test
    fun testSocialButtons() {
        testPage("social-buttons")
    }

    @Test
    fun testStyleTagsRemoval() {
        testPage("style-tags-removal")
    }

    @Test
    fun testSvgParsing() {
        testPage("svg-parsing")
    }

    @Test
    fun testTableStyleAttributes() {
        testPage("table-style-attributes")
    }

    @Test
    fun testTelegraph() {
        testPage("telegraph")
    }

    @Test
    fun testTitleAndH1Discrepancy() {
        testPage("title-and-h1-discrepancy")
    }

    @Test
    fun testTMZ1() {
        testPage("tmz-1")
    }

    @Test
    fun testTumblr() {
        testPage("tumblr")
    }

    @Test
    fun testWapo1() {
        testPage("wapo-1")
    }

    @Test
    fun testWapo2() {
        testPage("wapo-2")
    }

    @Test
    fun testWebmd1() {
        testPage("webmd-1")
    }

    @Test
    fun testWebmd2() {
        testPage("webmd-2")
    }

    @Test
    fun testWikia() {
        testPage("wikia")
    }

    @Test
    fun testWikipedia() {
        testPage("wikipedia")
    }

    @Test
    fun testWordpress() {
        testPage("wordpress")
    }

    @Test
    fun testYahoo1() {
        testPage("yahoo-1")
    }

    @Test
    fun testYahoo2() {
        testPage("yahoo-2")
    }

    @Test
    fun testYahoo3() {
        testPage("yahoo-3")
    }

    @Test
    fun testYahoo4() {
        testPage("yahoo-4")
    }

    @Test
    fun testYouth() {
        testPage("youth")
    }


    protected open fun testPage(pageName: String) {
        testPage(ReadabilityFakeTestUrl, "test-pages", pageName)
    }

}