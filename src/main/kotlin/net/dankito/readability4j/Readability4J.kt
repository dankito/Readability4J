package net.dankito.readability4j

import net.dankito.readability4j.model.ReadabilityOptions
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

    protected val options: ReadabilityOptions

    protected val preprocessor: Preprocessor

    protected val articleGrabber: ArticleGrabber

    protected val postprocessor: Postprocessor


    constructor(uri: String, html: String, options: ReadabilityOptions = ReadabilityOptions()) : this(uri, Jsoup.parse(html, uri), options)

    constructor(uri: String, document: Document, options: ReadabilityOptions = ReadabilityOptions()) {
        this.uri = uri
        this.document = document
        this.options = options

        this.preprocessor = Preprocessor()
        this.articleGrabber = ArticleGrabber(options)
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
        // Avoid parsing too large documents, as per configuration option
        if (options.maxElemsToParse > 0) {
            val numTags = document.getElementsByTag("*").size
            if(numTags > options.maxElemsToParse) {
                throw Exception("Aborting parsing document; $numTags elements found, but ReadabilityOption.maxElemsToParse is set to ${options.maxElemsToParse}")
            }
        }

        val article = Article(uri)

        preprocessor.prepareDocument(document)

        val contentElement = articleGrabber.grabArticle(document)
        log.info("Grabbed: $contentElement")

        contentElement?.let { articleContent ->  // TODO: or return null if grabbing didn't work?
            postprocessor.postProcessContent(articleContent, uri, options.additionalClassesToPreserve)

            article.content = articleContent.html() // TODO: but this removes paging information (pages in top node <div id="readability-content">)
        }

        return article
    }

}