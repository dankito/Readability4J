package net.dankito.readability4j

import net.dankito.readability4j.processor.ArticleGrabber
import net.dankito.readability4j.processor.Postprocessor
import net.dankito.readability4j.processor.Preprocessor
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory


open class Readability4J {

    companion object {
        private val log = LoggerFactory.getLogger(Readability4J::class.java)
    }


    protected val uri: String

    protected val document: Document

    protected val preprocessor: Preprocessor

    protected val articleGrabber: ArticleGrabber

    protected val postprocessor: Postprocessor


    constructor(uri: String, html: String) : this(uri, Jsoup.parse(html, uri))

    constructor(uri: String, document: Document) {
        this.uri = uri
        this.document = document

        this.preprocessor = Preprocessor()
        this.articleGrabber = ArticleGrabber()
        this.postprocessor = Postprocessor()
    }


    /**
     *
     * Runs readability.
     *
     * Workflow:
     *  1. Prep the document by removing script tags, css, etc.
     *  2. Build readability's DOM tree.
     *  3. Grab the article content from the current dom tree.
     *  4. Replace the current DOM tree with the new one.
     *  5. Read peacefully.
     *
     */
    open fun parse(): Article {
        // TODO: add and check _maxElemsToParse option

        val article = Article(uri)

        preprocessor.prepareDocument(document)

        val contentElement = articleGrabber.grabArticle(document)
        log.info("Grabbed: $contentElement")

        contentElement?.let { articleContent ->  // TODO: or return null if grabbing didn't work?
            postprocessor.postProcessContent(articleContent, uri /*, options.classesToPreserve*/)

            article.content = articleContent.html() // TODO: but this removes paging information (pages in top node <div id="readability-content">)
        }

        return article
    }

}