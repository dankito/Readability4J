package net.dankito.readability4j;


/**
 * Not a real test, just to have the example used in README.md as real Java code
 */
public class ReadMeCodeExample {

    public void codeExample() {
        String url = "";
        String html = "";

        Readability4J readability4J = new Readability4J(url, html); // url is just needed to resolve relative urls
        Article article = readability4J.parse();

        String extractedContentHtml = article.getContent();
        String extractedContentPlainText = article.getTextContent();
        String title = article.getTitle();
        String byline = article.getByline();
        String excerpt = article.getExcerpt();
    }
}
