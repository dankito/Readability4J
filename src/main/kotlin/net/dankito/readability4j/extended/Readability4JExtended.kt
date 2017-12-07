package net.dankito.readability4j.extended

import net.dankito.readability4j.Readability4J
import net.dankito.readability4j.extended.processor.ArticleGrabberExtended
import net.dankito.readability4j.extended.util.RegExUtilExtended
import net.dankito.readability4j.model.ReadabilityOptions
import net.dankito.readability4j.processor.MetadataParser
import net.dankito.readability4j.processor.Postprocessor
import net.dankito.readability4j.processor.Preprocessor
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class Readability4JExtended : Readability4J {

    // for Java interoperability
    /**
     * Calls Readability(String, String, ReadabilityOptions) with default ReadabilityOptions
     */
    constructor(uri: String, html: String) : this(uri, html, ReadabilityOptions())

    constructor(uri: String, html: String, options: ReadabilityOptions = ReadabilityOptions(), regExUtil: RegExUtilExtended = RegExUtilExtended(),
                preprocessor: Preprocessor = Preprocessor(regExUtil), metadataParser: MetadataParser = MetadataParser(regExUtil),
                articleGrabber: ArticleGrabberExtended = ArticleGrabberExtended(options, regExUtil), postprocessor: Postprocessor = Postprocessor())
            : this(uri, Jsoup.parse(html, uri), options, regExUtil, preprocessor, metadataParser, articleGrabber, postprocessor)

    // for Java interoperability
    /**
     * Calls Readability(String, Document, ReadabilityOptions) with default ReadabilityOptions
     */
    constructor(uri: String, document: Document) : this(uri, document, ReadabilityOptions())

    constructor(uri: String, document: Document, options: ReadabilityOptions = ReadabilityOptions(), regExUtil: RegExUtilExtended = RegExUtilExtended(),
                preprocessor: Preprocessor = Preprocessor(regExUtil), metadataParser: MetadataParser = MetadataParser(regExUtil),
                articleGrabber: ArticleGrabberExtended = ArticleGrabberExtended(options, regExUtil), postprocessor: Postprocessor = Postprocessor())
            : super(uri, document, options, regExUtil, preprocessor, metadataParser, articleGrabber, postprocessor)

}