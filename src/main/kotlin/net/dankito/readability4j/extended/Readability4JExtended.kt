package net.dankito.readability4j.extended

import net.dankito.readability4j.Readability4J
import net.dankito.readability4j.extended.util.DependencyResolverExtended
import net.dankito.readability4j.model.ReadabilityOptions
import org.jsoup.nodes.Document


open class Readability4JExtended : Readability4J {


    @JvmOverloads
    constructor(uri: String,
                html: String,
                options: ReadabilityOptions = ReadabilityOptions(),
                dependencyResolver: DependencyResolverExtended = DependencyResolverExtended())
            : super(uri, html, options, dependencyResolver)

    @JvmOverloads
    constructor(uri: String,
                document: Document,
                options: ReadabilityOptions = ReadabilityOptions(),
                dependencyResolver: DependencyResolverExtended = DependencyResolverExtended())
            : super(uri, document, options, dependencyResolver)


}