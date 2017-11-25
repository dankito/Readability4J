package net.dankito.readability4j.util

import com.fasterxml.jackson.databind.ObjectMapper
import net.dankito.readability4j.Article
import net.dankito.readability4j.Readability4J
import net.dankito.readability4j.model.ArticleMetadata
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit



fun main(args: Array<String>) {
    TestDataGenerator().generateTestData("", "") // set test case name and url here
}


class TestDataGenerator {

    companion object {
        const val DefaultUserAgent = ""

        const val DefaultCountRetries = 1

        val objectMapper = ObjectMapper()

        val client: OkHttpClient

        private val log = LoggerFactory.getLogger(TestDataGenerator::class.java)


        init {
            val builder = OkHttpClient.Builder()

            builder.followRedirects(true)
            builder.retryOnConnectionFailure(true)
            builder.connectTimeout(2000L, TimeUnit.MILLISECONDS)
            builder.readTimeout(2000L, TimeUnit.MILLISECONDS)
            builder.writeTimeout(2000L, TimeUnit.MILLISECONDS)

            client = builder.build()
        }
    }



    @Throws(Exception::class)
    fun generateTestData(testCaseName: String, url: String) {
        val webSiteHtml = getResponse(url)

        val readability = Readability4J(url, webSiteHtml)

        val article = readability.parse()

        writeTestData(webSiteHtml, article, testCaseName)
    }


    @Throws(Exception::class)
    private fun getResponse(url: String): String {
        try {
            val request = createGetRequest(url)

            val response = executeRequest(request, DefaultCountRetries)

            return response.body()?.string() ?: ""
        } catch (e: Exception) {
            log.error("Could not retrieve response from url $url", e)
            throw e
        }
    }

    @Throws(Exception::class)
    private fun executeRequest(request: Request, countRetries: Int = 0): Response {
        val response = client.newCall(request).execute()

        if(response.isSuccessful == false && countRetries > 0) {
            return executeRequest(request, countRetries - 1)
        }
        else {
            return response
        }
    }

    private fun createGetRequest(url: String, userAgent: String = DefaultUserAgent): Request {
        val requestBuilder = Request.Builder()

        requestBuilder.url(url)

        requestBuilder.header("User-Agent", userAgent)

        return requestBuilder.build()
    }


    private fun writeTestData(sourceHtml: String, article: Article, testCaseName: String) {
        val testPagesFolder = getPathToTestPagesFolder()

        val testCaseFolder = File(testPagesFolder, testCaseName)
        if(testCaseFolder.exists() == false) {
            testCaseFolder.mkdirs()
        }

        writeFile(testCaseFolder, "source.html", sourceHtml)
        writeFile(testCaseFolder, "expected.html", article.content ?: "")
        writeFile(testCaseFolder, "expected-metadata.json", generateMetadataJson(article))
    }

    private fun generateMetadataJson(article: Article): String {
        val metadata = ArticleMetadata(article.title, article.byline, article.excerpt, article.dir)

        return objectMapper.writeValueAsString(metadata)
    }

    private fun writeFile(testCaseFolder: File, fileName: String, fileContent: String) {
        val writer = OutputStreamWriter(FileOutputStream(File(testCaseFolder, fileName)), Charset.forName("UTF-8").newEncoder()) // TODO: set encoding

        writer.write(fileContent)

        writer.flush()
        writer.close()
    }

    private fun getPathToTestPagesFolder(): File {
        val testPageResourceUrl = this.javaClass.classLoader.getResource("logback-test.xml") // get url to any test resource that's for sure there
        val testPageResourceFile = File(testPageResourceUrl.toURI())

        val readability4JFolder = testPageResourceFile.parentFile.parentFile.parentFile.parentFile
        val testResourcesFolder = File(File(File(readability4JFolder, "src"), "test"), "resources")

        return File(testResourcesFolder, "additional-test-pages")
    }

}