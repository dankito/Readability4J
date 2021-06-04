package net.dankito.readability4j.model


open class ReadabilityOptions(val maxElemsToParse: Int = DEFAULT_MAX_ELEMS_TO_PARSE,
                              val nbTopCandidates: Int = DEFAULT_N_TOP_CANDIDATES,
                              val wordThreshold: Int = DEFAULT_WORD_THRESHOLD,
                              val characterThreshold : Int = DEFAULT_MIN_LENGTH,
                              val minAccumulatedScore : Int = DEFAULT_ACCUMULATED_SCORE,
                              val additionalClassesToPreserve: Collection<String> = ArrayList()) {

    companion object {
        // Max number of nodes supported by this parser. Default: 0 (no limit)
        const val DEFAULT_MAX_ELEMS_TO_PARSE = 0

        // The number of top candidates to consider when analysing how
        // tight the competition is among candidates.
        const val DEFAULT_N_TOP_CANDIDATES = 5

        // The default number of words an article must have in order to return a result
        const val DEFAULT_WORD_THRESHOLD = 500

        // The minimum character length an article must have in order to be readerable
        // default value set by Readablity.js is 140. We are setting 50 for our convenience
        const val DEFAULT_MIN_LENGTH = 50

        // The minimum accumulated score an article needs to have in order to be readerable
        const val DEFAULT_ACCUMULATED_SCORE = 20

    }

}