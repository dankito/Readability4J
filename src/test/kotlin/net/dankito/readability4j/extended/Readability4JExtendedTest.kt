package net.dankito.readability4j.extended

import net.dankito.readability4j.Readability4JTest
import net.dankito.readability4j.model.PageTestData


open class Readability4JExtendedTest : Readability4JTest() {

    override fun loadTestData(testPageFolderName: String, pageName: String): PageTestData {
        var testData = super.loadTestData(testPageFolderName, pageName)

        try {
            // check if test case has a different expected output placed in expected-extended.html
            val expectedExtendedOutput = getFileContentFromResource(testPageFolderName, pageName, "expected-extended.html")

            testData = PageTestData(testData.pageName, testData.sourceHtml, expectedExtendedOutput, testData.expectedMetadata)
        } catch(ignored: Exception) { }

        return testData
    }
}