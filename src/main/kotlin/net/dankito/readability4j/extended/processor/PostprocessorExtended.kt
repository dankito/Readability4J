package net.dankito.readability4j.extended.processor

import net.dankito.readability4j.processor.Postprocessor
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Tag


open class PostprocessorExtended : Postprocessor() {

    override fun fixRelativeUris(originalDocument: Document, element: Element, scheme: String, prePath: String,
								 pathBase: String) {

        val baseUrl = originalDocument.head().select("base").first()?.attr("href")

        if (baseUrl != null) { // if a base URL is specified use that one
            super.fixRelativeUris(originalDocument, element, scheme, prePath, baseUrl)
        }
        else {
            super.fixRelativeUris(originalDocument, element, scheme, prePath, pathBase)
        }
    }

    override fun fixRelativeImageUris(element: Element, scheme: String, prePath: String, pathBase: String) {
        super.fixRelativeImageUris(element, scheme, prePath, pathBase)

        fixAmpImageUris(element)
    }

    override fun fixRelativeImageUri(img: Element, scheme: String, prePath: String, pathBase: String) {
        val dataSrc = img.attr("data-src") // load data-src;
        val dataOriginal = img.attr("data-original")
        val dataActualSrc = img.attr("data-actualsrc")

        if(dataSrc.isNotBlank()) {
            img.attr("src", toAbsoluteURI(dataSrc, scheme, prePath, pathBase))
        }
        else if(dataOriginal.isNotBlank()) {
            img.attr("src", toAbsoluteURI(dataOriginal, scheme, prePath, pathBase))
        }
        else if(dataActualSrc.isNotBlank()) {
            img.attr("src", toAbsoluteURI(dataActualSrc, scheme, prePath, pathBase))
        }
        else {
            super.fixRelativeImageUri(img, scheme, prePath, pathBase)
        }
    }

    protected open fun fixAmpImageUris(element: Element) {
        element.getElementsByTag("amp-img").forEach { amp_img ->

            if (amp_img.childNodeSize() == 0) {
                val attributes = Attributes()
                attributes.put("decoding", "async")
                attributes.put("alt", amp_img.attr("alt"))
                attributes.put("srcset", amp_img.attr("srcset").trim())

                amp_img.appendChild(Element(Tag.valueOf("img"), "", attributes))
            }
        }
    }

}