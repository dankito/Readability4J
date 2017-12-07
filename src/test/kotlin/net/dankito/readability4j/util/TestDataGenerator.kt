package net.dankito.readability4j.util

import net.dankito.readability4j.Readability4J
import net.dankito.readability4j.extended.Readability4JExtended
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit



fun main(args: Array<String>) {
    TestDataGenerator().generateTestData("additional-test-pages", "",
            "") // set test case name and url here
}


class TestDataGenerator : TestDataGeneratorBase() {

    companion object {
        const val DefaultUserAgent = ""

        const val DefaultCountRetries = 1

        val client: OkHttpClient

        private val log = LoggerFactory.getLogger(TestDataGenerator::class.java)


        init {
            val builder = OkHttpClient.Builder()

            builder.followRedirects(true)
            builder.retryOnConnectionFailure(true)
            builder.connectTimeout(2000L, TimeUnit.SECONDS)
            builder.readTimeout(2000L, TimeUnit.SECONDS)
            builder.writeTimeout(2000L, TimeUnit.SECONDS)

            client = builder.build()
        }
    }



    @Throws(Exception::class)
    fun generateTestData(testFolderName: String, testCaseName: String, url: String) {
        val webSiteHtml = getResponse(url)

        val readability = Readability4J(url, webSiteHtml)
        val article = readability.parse()

        val readabilityExtended = Readability4JExtended(url, webSiteHtml)
        val articleExtended = readabilityExtended.parse()

        writeTestData(webSiteHtml, article, articleExtended, testFolderName, testCaseName)
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

}