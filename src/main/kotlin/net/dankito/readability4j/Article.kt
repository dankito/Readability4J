package net.dankito.readability4j


open class Article(

        /**
         * Original uri object that was passed to constructor
         */
        val uri: String,

        /**
         * Article title
         */
        var title: String? = null,

        /**
         * HTML string of processed article content
         */
        var content: String? = null,

        /**
         * Length of article, in characters
         */
        var length: Int? = null,

        /**
         * Article description, or short excerpt from content
         */
        var excerpt: String? = null,

        /**
         * Author metadata
         */
        var byline: String? = null,

        /**
         * Content direction
         */
        var dir: String? = null

)