package net.dankito.readability4j.util

import com.fasterxml.jackson.databind.ObjectMapper
import net.dankito.readability4j.Article
import net.dankito.readability4j.model.ArticleMetadata
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.Charset


abstract class TestDataGeneratorBase {

    companion object {
        val objectMapper = ObjectMapper()

        private val log = LoggerFactory.getLogger(TestDataGeneratorBase::class.java)
    }


    protected open fun writeTestData(sourceHtml: String, article: Article, testFolderName: String, testCaseName: String) {
        val testPagesFolder = File(getPathToTestResourcesFolder(), testFolderName)

        val testCaseFolder = File(testPagesFolder, testCaseName)
        if(testCaseFolder.exists() == false) {
            testCaseFolder.mkdirs()
        }

        writeFile(testCaseFolder, "source.html", sourceHtml)
        writeFile(testCaseFolder, "expected.html", article.content ?: "")
        writeFile(testCaseFolder, "expected-metadata.json", generateMetadataJson(article))
    }

    protected open fun generateMetadataJson(article: Article): String {
        val metadata = ArticleMetadata(article.title, article.byline, article.excerpt, article.dir)

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(metadata)
    }

    protected open fun writeFile(testCaseFolder: File, fileName: String, fileContent: String) {
        val writer = OutputStreamWriter(FileOutputStream(File(testCaseFolder, fileName)), Charset.forName("UTF-8").newEncoder()) // TODO: set encoding

        writer.write(fileContent)

        writer.flush()
        writer.close()
    }

    protected open fun getPathToTestResourcesFolder(): File {
        val testPageResourceUrl = this.javaClass.classLoader.getResource("logback-test.xml") // get url to any test resource that's for sure there
        val testPageResourceFile = File(testPageResourceUrl.toURI())

        val readability4JFolder = testPageResourceFile.parentFile.parentFile.parentFile.parentFile

        return File(File(File(readability4JFolder, "src"), "test"), "resources")
    }

}