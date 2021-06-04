package net.dankito.readability4j

import net.dankito.readability4j.model.ArticleMetadata
import net.dankito.readability4j.model.ReadabilityOptions
import net.dankito.readability4j.processor.ArticleGrabber
import net.dankito.readability4j.processor.MetadataParser
import net.dankito.readability4j.processor.Postprocessor
import net.dankito.readability4j.processor.Preprocessor
import net.dankito.readability4j.util.RegExUtil
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.jsoup.select.Evaluator
import org.slf4j.LoggerFactory
import kotlin.math.sqrt


open class Readability4J {

    companion object {
        private val log = LoggerFactory.getLogger(Readability4J::class.java)

        var REGEXPS = mapOf(
            // NOTE: These two regular expressions are duplicated in
            // Readability.js. Please keep both copies in sync.
            "unlikelyCandidates" to  "/-ad-|ai2html|banner|breadcrumbs|combx|comment|community|cover-wrap|disqus|extra|footer|gdpr|header|legends|menu|related|remark|replies|rss|shoutbox|sidebar|skyscraper|social|sponsor|supplemental|ad-break|agegate|pagination|pager|popup|yom-remote/i".toRegex(),
            "okMaybeItsACandidate" to "/and|article|body|column|content|main|shadow/i".toRegex()
        );
    }


    protected val uri: String

    protected val document: Document

    protected val options: ReadabilityOptions

    protected val regEx: RegExUtil

    protected val preprocessor: Preprocessor

    protected val metadataParser: MetadataParser

    protected val articleGrabber: ArticleGrabber

    protected val postprocessor: Postprocessor


    // TODO: add IDependencyResolver interface and @JvmOverloads

    // for Java interoperability
    /**
     * Calls Readability(String, String, ReadabilityOptions) with default ReadabilityOptions
     */
    constructor(uri: String, html: String) : this(uri, html, ReadabilityOptions())

    constructor(uri: String, html: String, options: ReadabilityOptions = ReadabilityOptions(), regExUtil: RegExUtil = RegExUtil(),
                preprocessor: Preprocessor = Preprocessor(regExUtil), metadataParser: MetadataParser = MetadataParser(regExUtil),
                articleGrabber: ArticleGrabber = ArticleGrabber(options, regExUtil), postprocessor: Postprocessor = Postprocessor())
            : this(uri, Jsoup.parse(html, uri), options, regExUtil, preprocessor, metadataParser, articleGrabber, postprocessor)

    // for Java interoperability
    /**
     * Calls Readability(String, Document, ReadabilityOptions) with default ReadabilityOptions
     */
    constructor(uri: String, document: Document) : this(uri, document, ReadabilityOptions())

    constructor(uri: String, document: Document, options: ReadabilityOptions = ReadabilityOptions(), regExUtil: RegExUtil = RegExUtil(),
                preprocessor: Preprocessor = Preprocessor(regExUtil), metadataParser: MetadataParser = MetadataParser(regExUtil),
                articleGrabber: ArticleGrabber = ArticleGrabber(options, regExUtil), postprocessor: Postprocessor = Postprocessor()) {
        this.uri = uri
        this.document = document
        this.options = options

        this.regEx = regExUtil
        this.preprocessor = preprocessor
        this.metadataParser = metadataParser
        this.articleGrabber = articleGrabber
        this.postprocessor = postprocessor
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

        val metadata = metadataParser.getArticleMetadata(document)

        val articleContent = articleGrabber.grabArticle(document, metadata)
        log.debug("Grabbed: {}", articleContent)

        articleContent?.let { // TODO: or return null if grabbing didn't work?
            postprocessor.postProcessContent(document, articleContent, uri, options.additionalClassesToPreserve)

            article.articleContent = articleContent
        }

        setArticleMetadata(article, metadata, articleContent)

        return article
    }

    fun isProbablyReaderable() : Boolean {
        val paragraphNodes = document.select("p")
        val preNodes = document.select("pre")
        val nodes = Elements()
        nodes.addAll(paragraphNodes);
        nodes.addAll(preNodes);

        val brNodes = document.select("div > br")

        if(brNodes.isNotEmpty()) {
            brNodes.forEach { brNode ->
                brNode.parent()?.let {
                    nodes.add(it)
                }
            }
        }

        var score = 0.0
        nodes.forEach { node ->

            val matchString = node.className() + " " + node.id();
            if (REGEXPS["unlikelyCandidates"]?.matches(matchString) == true &&
                REGEXPS["okMaybeItsACandidate"]?.matches(matchString) == false) {
                return false;
            }

            if(nodes.select("li p").isNotEmpty()) {
                return false
            }

            val textContentLength = node.text().trim().length;
            if (textContentLength < options.characterThreshold) {
                return false;
            }

            score += sqrt((textContentLength - options.characterThreshold).toDouble());
        }



        return score >= options.minAccumulatedScore
    }

    private fun setArticleMetadata(article: Article, metadata: ArticleMetadata, articleContent: Element?) {
        // If we haven't found an excerpt in the article's metadata, use the article's
        // first paragraph as the excerpt. This is used for displaying a preview of
        // the article's content.
        if(metadata.excerpt.isNullOrBlank()) {
            articleContent?.getElementsByTag("p")?.first()?.let { firstParagraph ->
                metadata.excerpt = firstParagraph.text().trim()
            }
        }

        article.title = metadata.title
        article.byline = if(metadata.byline.isNullOrBlank()) articleGrabber.articleByline else metadata.byline
        article.dir = articleGrabber.articleDir
        article.excerpt = metadata.excerpt
    }

}