package net.dankito.readability4j.extended.processor

import net.dankito.readability4j.processor.Postprocessor
import org.jsoup.nodes.Element


open class PostprocessorExtended : Postprocessor() {

    override fun fixRelativeImageUri(img: Element, scheme: String, prePath: String, pathBase: String) {
        val dataSrc = img.attr("data-src") // load data-src;
        if(dataSrc.isNotBlank()) {
            img.attr("src", toAbsoluteURI(dataSrc, scheme, prePath, pathBase))
        }
        else {
            super.fixRelativeImageUri(img, scheme, prePath, pathBase)
        }
    }

}