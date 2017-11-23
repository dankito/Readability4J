package net.dankito.readability4j

import org.jsoup.nodes.Element


open class Article(

        /**
         * Original uri object that was passed to constructor
         */
        val uri: String

) {

        /**
         * Article title
         */
        var title: String? = null

        var articleContent: Element? = null

        /**
         * HTML string of processed article content
         */
        val content: String?
                get() = articleContent?.html() // TODO: but this removes paging information (pages in top node <div id="readability-content">)

        val textContent: String?
                get() = articleContent?.text()

        /**
         * Length of article, in characters
         */
        var length: Int = -1
                get() = textContent?.length ?: -1

        /**
         * Article description, or short excerpt from content
         */
        var excerpt: String? = null

        /**
         * Author metadata
         */
        var byline: String? = null

        /**
         * Content direction
         */
        var dir: String? = null

}