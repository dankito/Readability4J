package net.dankito.readability4j.util

import net.dankito.readability4j.model.ReadabilityOptions
import net.dankito.readability4j.processor.ArticleGrabber
import net.dankito.readability4j.processor.MetadataParser
import net.dankito.readability4j.processor.Postprocessor
import net.dankito.readability4j.processor.Preprocessor


interface IDependencyResolver {

    fun createRegExUtil(): RegExUtil

    fun createPreprocessor(regExUtil: RegExUtil): Preprocessor

    fun createMetadataParser(regExUtil: RegExUtil): MetadataParser

    fun createArticleGrabber(options: ReadabilityOptions, regExUtil: RegExUtil): ArticleGrabber

    fun createPostprocessor(): Postprocessor

}