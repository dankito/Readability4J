package net.dankito.readability4j.extended.util

import net.dankito.readability4j.util.RegExUtil
import java.util.regex.Pattern


open class RegExUtilExtended : RegExUtil {

    companion object {
        const val RemoveImageDefaultPattern = "author|avatar|thumbnail" // CHANGE: this is not in Mozilla's Readability
    }


    protected val removeImage: Pattern


    constructor(unlikelyCandidatesPattern: String = UnlikelyCandidatesDefaultPattern, okMaybeItsACandidatePattern: String = OkMaybeItsACandidateDefaultPattern,
                positivePattern: String = PositiveDefaultPattern, negativePattern: String = NegativeDefaultPattern,
                extraneousPattern: String = ExtraneousDefaultPattern, bylinePattern: String = BylineDefaultPattern,
                replaceFontsPattern: String = ReplaceFontsDefaultPattern, normalizePattern: String = NormalizeDefaultPattern,
                videosPattern: String = VideosDefaultPattern, nextLinkPattern: String = NextLinkDefaultPattern,
                prevLinkPattern: String = PrevLinkDefaultPattern, whitespacePattern: String = WhitespaceDefaultPattern,
                hasContentPattern: String = HasContentDefaultPattern, removeImagePattern: String = RemoveImageDefaultPattern)
    : super(unlikelyCandidatesPattern, okMaybeItsACandidatePattern, positivePattern, negativePattern, extraneousPattern, bylinePattern, replaceFontsPattern, normalizePattern,
            videosPattern, nextLinkPattern, prevLinkPattern, whitespacePattern, hasContentPattern) {
        this.removeImage = Pattern.compile(removeImagePattern)
    }


    open fun keepImage(matchString: String): Boolean { // CHANGE: this is not in Mozilla's Readability
        if((isNegative(matchString) && isPositive(matchString) == false) || removeImage.matcher(matchString).find()) {
            return false
        }

        return true
    }

}