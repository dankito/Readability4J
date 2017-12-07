# Readability4

Readability4J is a Kotlin port of Mozilla's Readability.js, which is used for Firefox's reader view: https://github.com/mozilla/readability.

It tries to detect the relevant content of a website and removes all clutter from it such as advertisements, navigation bars, social media buttons, etc.

The extracted text then can be used for indexing web pages, to provide the user a pleasant reading experience and similar.

As it‘s compatible with Mozilla‘s Readability.js it produces exact the same output as you would see in Firefox‘s Reader View (just some white spaces differ due to Jsoup‘s different formatting, but you can‘t see them anyway).

## Setup

Gradle:
```
dependencies {
  compile 'net.dankito.readability4j:readability4j:1.0'
}
```

Maven:
```
<dependency>
   <groupId>net.dankito.readability4j</groupId>
   <artifactId>readability4j</artifactId>
   <version>1.0</version>
</dependency>
```


## Usage

```java
String url = ...;
String html = ...;

Readability4J readability4J = new Readability4J(url, html); // url is just needed to resolve relative urls
Article article = readability4J.parse();

String extractedContentHtml = article.getContent();
String extractedContentPlainText = article.getTextContent();
String title = article.getTitle();
String byline = article.getByline();
String excerpt = article.getExcerpt();
```

## Compatibility with Mozilla‘s Readability.js

As mentioned before, this is almost an exact copy of Mozilla's Readability.js. But since I didn't find the original code very readable itself, I extracted some parts from the 2000 lines of code into a new classes:

<table>
    <tr>
        <th>Readability.js function</td>
        <th>Readability4J location</td>
    </tr>
    <tr>
        <td>_removeScripts() and _prepDocument()</td>
        <td>Preprocessor.prepareDocument()</td>
    </tr>
    <tr>
        <td>_grabArticle()</td>
        <td>ArticleGrabber.grabArticle()</td>
    </tr>
    <tr>
        <td>_postProcessContent()</td>
        <td>Postprocessor.postProcessContent()</td>
    </tr>
    <tr>
        <td>_getArticleMetadata()</td>
        <td>MetadataParser.getArticleMetadata()</td>
    </tr>
</table>


Overview of which Mozilla‘s Readability.js commit a Readability4J version matches:

<table>
    <tr>
        <th>Version</td>
        <th>Commit</td>
        <th>Date</td>
    </tr>
    <tr>
        <td>1.0</td>
        <td>8da91b9</td>
        <td>12/5/17</td>
    </tr>
</table>

## Extensibility

I tried to create the library as extensible as possible. All above mentioned classes can be overwritten and passed to Readability4J's constructor.

In the extended package I also implemented some additional features Readability.js doesn‘t have like keeping more images, loading <img> data-src attributes and more.

If you like you use these, simply instantiate a `Readability4JExtended` object instead if `Readability4`.

# License

    Copyright 2017 dankito

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.