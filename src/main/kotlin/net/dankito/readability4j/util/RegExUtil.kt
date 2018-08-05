package net.dankito.readability4j.util

import java.util.regex.Pattern


open class RegExUtil {

    companion object {
        const val UnlikelyCandidatesDefaultPattern = "banner|breadcrumbs|combx|comment|community|cover-wrap|disqus|extra|" +
                "foot|header|legends|menu|related|remark|replies|rss|shoutbox|sidebar|skyscraper|social|sponsor|supplemental|" +
                "ad-break|agegate|pagination|pager|popup|yom-remote"

        const val OkMaybeItsACandidateDefaultPattern = "and|article|body|column|main|shadow"

        const val PositiveDefaultPattern = "article|body|content|entry|hentry|h-entry|main|page|pagination|post|text|blog|story"

        const val NegativeDefaultPattern = "hidden|^hid$| hid$| hid |^hid |banner|combx|comment|com-|contact|foot|footer|footnote|" +
                "masthead|media|meta|outbrain|promo|related|scroll|share|shoutbox|sidebar|skyscraper|sponsor|shopping|tags|tool|widget"

        const val ExtraneousDefaultPattern = "print|archive|comment|discuss|e[\\-]?mail|share|reply|all|login|sign|single|utility"

        const val BylineDefaultPattern = "byline|author|dateline|writtenby|p-author"

        const val ReplaceFontsDefaultPattern = "<(/?)font[^>]*>"

        const val NormalizeDefaultPattern = "\\s{2,}"

        const val VideosDefaultPattern = "//(www\\.)?(dailymotion|youtube|youtube-nocookie|player\\.vimeo)\\.com"

        const val NextLinkDefaultPattern = "(next|weiter|continue|>([^\\|]|$)|»([^\\|]|$))"

        const val PrevLinkDefaultPattern = "(prev|earl|old|new|<|«)"

        const val WhitespaceDefaultPattern = "^\\s*$"

        const val HasContentDefaultPattern = "\\S$"
    }


    protected val unlikelyCandidates: Pattern

    protected val okMaybeItsACandidate: Pattern

    protected val positive: Pattern

    protected val negative: Pattern

    protected val extraneous: Pattern

    protected val byline: Pattern

    protected val replaceFonts: Pattern

    protected val normalize: Pattern

    protected val videos: Pattern

    protected val nextLink: Pattern

    protected val prevLink: Pattern

    protected val whitespace: Pattern

    protected val hasContent: Pattern


    constructor(unlikelyCandidatesPattern: String = UnlikelyCandidatesDefaultPattern, okMaybeItsACandidatePattern: String = OkMaybeItsACandidateDefaultPattern,
                positivePattern: String = PositiveDefaultPattern, negativePattern: String = NegativeDefaultPattern,
                extraneousPattern: String = ExtraneousDefaultPattern, bylinePattern: String = BylineDefaultPattern,
                replaceFontsPattern: String = ReplaceFontsDefaultPattern, normalizePattern: String = NormalizeDefaultPattern,
                videosPattern: String = VideosDefaultPattern, nextLinkPattern: String = NextLinkDefaultPattern,
                prevLinkPattern: String = PrevLinkDefaultPattern, whitespacePattern: String = WhitespaceDefaultPattern,
                hasContentPattern: String = HasContentDefaultPattern) {
        this.unlikelyCandidates = Pattern.compile(unlikelyCandidatesPattern, Pattern.CASE_INSENSITIVE)
        this.okMaybeItsACandidate = Pattern.compile(okMaybeItsACandidatePattern, Pattern.CASE_INSENSITIVE)
        this.positive = Pattern.compile(positivePattern, Pattern.CASE_INSENSITIVE)
        this.negative = Pattern.compile(negativePattern, Pattern.CASE_INSENSITIVE)
        this.extraneous = Pattern.compile(extraneousPattern, Pattern.CASE_INSENSITIVE)
        this.byline = Pattern.compile(bylinePattern, Pattern.CASE_INSENSITIVE)
        this.replaceFonts = Pattern.compile(replaceFontsPattern, Pattern.CASE_INSENSITIVE)
        this.normalize = Pattern.compile(normalizePattern)
        this.videos = Pattern.compile(videosPattern, Pattern.CASE_INSENSITIVE)
        this.nextLink = Pattern.compile(nextLinkPattern, Pattern.CASE_INSENSITIVE)
        this.prevLink = Pattern.compile(prevLinkPattern, Pattern.CASE_INSENSITIVE)
        this.whitespace = Pattern.compile(whitespacePattern)
        this.hasContent = Pattern.compile(hasContentPattern)
    }


    open fun isPositive(matchString: String): Boolean {
        return positive.matcher(matchString).find()
    }

    open fun isNegative(matchString: String): Boolean {
        return negative.matcher(matchString).find()
    }

    open fun isUnlikelyCandidate(matchString: String): Boolean {
        return unlikelyCandidates.matcher(matchString).find()
    }

    open fun okMaybeItsACandidate(matchString: String): Boolean {
        return okMaybeItsACandidate.matcher(matchString).find()
    }

    open fun isByline(matchString: String): Boolean {
        return byline.matcher(matchString).find()
    }

    open fun hasContent(matchString: String): Boolean {
        return hasContent.matcher(matchString).find()
    }

    open fun isWhitespace(matchString: String): Boolean {
        return whitespace.matcher(matchString).find()
    }

    open fun normalize(text: String): String {
        return normalize.matcher(text).replaceAll(" ")
    }

    open fun isVideo(matchString: String): Boolean {
        return videos.matcher(matchString).find()
    }

}