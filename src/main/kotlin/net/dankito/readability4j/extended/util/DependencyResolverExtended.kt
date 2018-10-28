package net.dankito.readability4j.extended.util

import net.dankito.readability4j.extended.processor.ArticleGrabberExtended
import net.dankito.readability4j.extended.processor.PostprocessorExtended
import net.dankito.readability4j.model.ReadabilityOptions
import net.dankito.readability4j.processor.ArticleGrabber
import net.dankito.readability4j.processor.Postprocessor
import net.dankito.readability4j.util.DefaultDependencyResolver
import net.dankito.readability4j.util.RegExUtil


open class DependencyResolverExtended : DefaultDependencyResolver() {

    override fun createRegExUtil(): RegExUtil {
        return RegExUtilExtended()
    }

    override fun createArticleGrabber(options: ReadabilityOptions, regExUtil: RegExUtil): ArticleGrabber {
        return ArticleGrabberExtended(options, regExUtil as RegExUtilExtended)
    }

    override fun createPostprocessor(): Postprocessor {
        return PostprocessorExtended()
    }

}