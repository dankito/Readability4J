package net.dankito.readability4j.util

import net.dankito.readability4j.model.ReadabilityOptions
import net.dankito.readability4j.processor.ArticleGrabber
import net.dankito.readability4j.processor.MetadataParser
import net.dankito.readability4j.processor.Postprocessor
import net.dankito.readability4j.processor.Preprocessor


open class DefaultDependencyResolver : IDependencyResolver {

    override fun createRegExUtil(): RegExUtil {
        return RegExUtil()
    }

    override fun createPreprocessor(regExUtil: RegExUtil): Preprocessor {
        return Preprocessor(regExUtil)
    }

    override fun createMetadataParser(regExUtil: RegExUtil): MetadataParser {
        return MetadataParser(regExUtil)
    }

    override fun createArticleGrabber(options: ReadabilityOptions, regExUtil: RegExUtil): ArticleGrabber {
        return ArticleGrabber(options, regExUtil)
    }

    override fun createPostprocessor(): Postprocessor {
        return Postprocessor()
    }

}