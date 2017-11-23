package net.dankito.readability4j.model


data class PageTestData(val pageName: String,
                        val sourceHtml: String,
                        val expectedOutput: String,
                        val expectedOutputReal: String,
                        val expectedMetadata: ArticleMetadata)