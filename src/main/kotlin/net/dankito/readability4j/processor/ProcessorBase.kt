package net.dankito.readability4j.processor

import net.dankito.readability4j.util.RegExUtil
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import org.slf4j.LoggerFactory

/**
 * Contains common utils for Preprocessor and Postprocessor
 */
abstract class ProcessorBase {

    companion object {
        protected const val TruncateLogOutput = false

        private val log = LoggerFactory.getLogger(ProcessorBase::class.java)
    }


    protected open fun removeNodes(element: Element, tagName: String, filterFunction: ((Element) -> Boolean)? = null) {
        element.getElementsByTag(tagName).reversed().forEach { childElement ->
            if(childElement.parentNode() != null) {
                if(filterFunction == null || filterFunction(childElement)) {
                    printAndRemove(childElement, "removeNode('$tagName')")
                }
            }
        }
    }

    protected open fun printAndRemove(node: Node, reason: String) {
        if(node.parent() != null) {
            logNodeInfo(node, reason)
            node.remove()
        }
    }

    protected open fun logNodeInfo(node: Node, reason: String) {
        val nodeToString =
        if(TruncateLogOutput)
            node.outerHtml().substring(0, Math.min(node.outerHtml().length, 80)).replace("\n", "")
        else
            "\n------\n" + node.outerHtml() + "\n------\n"

        log.info("$reason [$nodeToString]")
    }


    protected open fun replaceNodes(parentElement: Element, tagName: String, newTagName: String) {
        parentElement.getElementsByTag(tagName).forEach { element ->
            element.tagName(newTagName)
        }
    }


    /**
     * Finds the next element, starting from the given node, and ignoring
     * whitespace in between. If the given node is an element, the same node is
     * returned.
     */
    protected open fun nextElement(node: Node?, regEx: RegExUtil): Element? {
        var next: Node? = node

        while(next != null
                && (next is Element == false)
                && (next is TextNode && regEx.isWhitespace(next.text()))) {
            next = next.nextSibling()
        }

        return next as? Element
    }

    /**
     * Get the inner text of a node - cross browser compatibly.
     * This also strips out any excess whitespace to be found.
     */
    protected open fun getInnerText(e: Element, regEx: RegExUtil? = null, normalizeSpaces: Boolean = true): String {
        val textContent = e.text().trim()

        if(normalizeSpaces && regEx != null) {
            return regEx.normalize(textContent)
        }

        return textContent
    }

}
