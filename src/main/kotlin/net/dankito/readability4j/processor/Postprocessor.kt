package net.dankito.readability4j.processor

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.slf4j.LoggerFactory
import java.net.URI
import java.util.Arrays
import java.util.regex.Pattern


open class Postprocessor {
    
    companion object {
        val AbsoluteUriPattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9\\+\\-\\.]*:")


        // These are the classes that readability sets itself.
        val CLASSES_TO_PRESERVE = Arrays.asList("readability-styled", "page")

        private val log = LoggerFactory.getLogger(Postprocessor::class.java)
    }
    

    open fun postProcessContent(originalDocument: Document, articleContent: Element, articleUri: String,
                                additionalClassesToPreserve: Collection<String> = emptyList()) {
        // Readability cannot open relative uris so we convert them to absolute uris.
        fixRelativeUris(originalDocument, articleContent, articleUri)

        // Remove IDs and classes.
        // Remove classes.
        val classesToPreserve = Arrays.asList(CLASSES_TO_PRESERVE, additionalClassesToPreserve).flatten().toSet()
        cleanClasses(articleContent, classesToPreserve)
    }


    /**
     * Converts each <a> and <img> uri in the given element to an absolute URI,
     * ignoring #ref URIs.
     */
    protected open fun fixRelativeUris(originalDocument: Document, element: Element, articleUri: String) {
        try {
            val uri = URI.create(articleUri)
            val scheme = uri.scheme
            val prePath = uri.scheme + "://" + uri.host
            val pathBase = uri.scheme + "://" + uri.host + uri.path.substring(0, uri.path.lastIndexOf("/") + 1)

            fixRelativeUris(originalDocument, element, scheme, prePath, pathBase)
        } catch(e: Exception) { log.error("Could not fix relative urls for $element with base uri $articleUri", e) }
    }

    protected open fun fixRelativeUris(originalDocument: Document, element: Element, scheme: String, prePath: String,
                                       pathBase: String) {

        fixRelativeAnchorUris(element, scheme, prePath, pathBase)

        fixRelativeImageUris(element, scheme, prePath, pathBase)
    }

    protected open fun fixRelativeAnchorUris(element: Element, scheme: String, prePath: String, pathBase: String) {
        element.getElementsByTag("a").forEach { link ->
            val href = link.attr("href")
            if(href.isNotBlank()) {
                // Replace links with javascript: URIs with text content, since
                // they won't work after scripts have been removed from the page.
                if(href.indexOf("javascript:") == 0) {
                    val text = TextNode(link.wholeText())
                    link.replaceWith(text)
                }
                else {
                    link.attr("href", toAbsoluteURI(href, scheme, prePath, pathBase))
                }
            }
        }
    }

    protected open fun fixRelativeImageUris(element: Element, scheme: String, prePath: String, pathBase: String) {
        element.getElementsByTag("img").forEach { img ->
            fixRelativeImageUri(img, scheme, prePath, pathBase)
        }
    }

    protected open fun fixRelativeImageUri(img: Element, scheme: String, prePath: String, pathBase: String) {
        val src = img.attr("src")

        if(src.isNotBlank()) {
            img.attr("src", toAbsoluteURI(src, scheme, prePath, pathBase))
        }
    }

    protected open fun toAbsoluteURI(uri: String, scheme: String, prePath: String, pathBase: String): String {
        // If this is already an absolute URI, return it.
        if(isAbsoluteUri(uri) || uri.length <= 2) {
            return uri
        }

        // Scheme-rooted relative URI.
        if(uri.substring(0, 2) == "//") {
            return scheme + "://" + uri.substring(2)
        }

        // Prepath-rooted relative URI.
        if(uri[0] == '/') {
            return prePath + uri
        }

        // Dotslash relative URI.
        if(uri.indexOf("./") == 0) {
            return pathBase + uri.substring(2)
        }

        // Ignore hash URIs:
        if(uri[0] == '#') {
            return uri
        }

        // Standard relative URI add entire path. pathBase already includes a
        // trailing "/".
        return pathBase + uri
    }
    
    protected open fun isAbsoluteUri(uri: String): Boolean {
        return AbsoluteUriPattern.matcher(uri).find()
    }


    /**
     * Removes the class="" attribute from every element in the given
     * subtree, except those that match CLASSES_TO_PRESERVE and
     * the classesToPreserve array from the options object.
     */
    protected open fun cleanClasses(node: Element, classesToPreserve: Set<String>) {
        val classNames = node.classNames().filter { classesToPreserve.contains(it) }

        if(classNames.isNotEmpty()) {
            node.classNames(classNames.toMutableSet())
        }
        else {
            node.removeAttr("class")
        }

        node.children().forEach { child ->
            cleanClasses(child, classesToPreserve)
        }
    }

}