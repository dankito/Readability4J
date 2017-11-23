package net.dankito.readability4j.processor

import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import java.net.URI
import java.util.*
import java.util.regex.Pattern


open class Postprocessor {
    
    companion object {
        val AbsoluteUriPattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9\\+\\-\\.]*:")


        // These are the IDs and classes that readability sets itself.
        val IDS_TO_PRESERVE = Arrays.asList("readability-content", "readability-page-1")
        val CLASSES_TO_PRESERVE = Arrays.asList("readability-styled", "page")
    }
    

    open fun postProcessContent(articleContent: Element, articleUri: String, additionalClassesToPreserve: Collection<String> = emptyList()) {
        // Readability cannot open relative uris so we convert them to absolute uris.
        fixRelativeUris(articleContent, articleUri)

        // Remove IDs and classes.
        val classesToPreserve = Arrays.asList(CLASSES_TO_PRESERVE, additionalClassesToPreserve).flatten().toSet()
        cleanIDsAndClasses(articleContent, classesToPreserve)
    }


    /**
     * Converts each <a> and <img> uri in the given element to an absolute URI,
     * ignoring #ref URIs.
     */
    protected open fun fixRelativeUris(element: Element, articleUri: String) {
        val uri = URI.create(articleUri)
        val scheme = uri.scheme
        val prePath = uri.scheme + "://" + uri.host
        val pathBase = uri.scheme + "://" + uri.host + uri.path.substring(0, uri.path.lastIndexOf("/") + 1) // TODO: catch exceptions

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

        element.getElementsByTag("img").forEach { img ->
            val src = img.attr("src")
            if(src.isNotBlank()) {
                img.attr("src", toAbsoluteURI(src, scheme, prePath, pathBase))
            }
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
     * Removes the id="" and class="" attribute from every element in the given
     * subtree, except those that match IDS_TO_PRESERVE, CLASSES_TO_PRESERVE and
     * the classesToPreserve array from the options object.
     */
    protected open fun cleanIDsAndClasses(node: Element, classesToPreserve: Set<String>) {
        if(IDS_TO_PRESERVE.contains(node.id()) == false) {
            node.removeAttr("id")
        }

        val classNames = node.classNames().filter { classesToPreserve.contains(it) }

        if(classNames.isNotEmpty()) {
            node.classNames(classNames.toMutableSet())
        }
        else {
            node.removeAttr("class")
        }

        node.children().forEach { child ->
            cleanIDsAndClasses(child, classesToPreserve)
        }
    }

}