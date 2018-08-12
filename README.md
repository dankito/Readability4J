# Readability4

Readability4J is a Kotlin port of Mozilla's Readability.js, which is used for Firefox's reader view: https://github.com/mozilla/readability.

It tries to detect the relevant content of a website and removes all clutter from it such as advertisements, navigation bars, social media buttons, etc.

The extracted text then can be used for indexing web pages, to provide the user a pleasant reading experience and similar.

As it‘s compatible with Mozilla‘s Readability.js it produces exact the same output as you would see in Firefox‘s Reader View (just some white spaces differ due to Jsoup‘s different formatting, but you can‘t see them anyway).

## Setup

Gradle:
```
dependencies {
  compile 'net.dankito.readability4j:readability4j:1.0.1'
}
```

Maven:
```
<dependency>
   <groupId>net.dankito.readability4j</groupId>
   <artifactId>readability4j</artifactId>
   <version>1.0.1</version>
</dependency>
```


## Usage

```java
String url = ...;
String html = ...;

Readability4J readability4J = new Readability4J(url, html); // url is just needed to resolve relative urls
Article article = readability4J.parse();

// returns extracted content in a <div> element
String extractedContentHtml = article.getContent();
// to get content wrapped in <html> tags and encoding set to UTF-8, see chapter 'Output encoding'
String extractedContentHtmlWithUtf8Encoding = article.getContentWithUtf8Encoding();
String extractedContentPlainText = article.getTextContent();
String title = article.getTitle();
String byline = article.getByline();
String excerpt = article.getExcerpt();
```

## Readability4J and Readability4JExtended

With Readability4J class I wanted to stick close to Mozilla's Readability to keep compatibility.

But during development I found some handy features not supported by Readability, e. g. copying url from data-src 
attribute to &lt;img src="" /> to display lazy loading images, using &lt;head>&lt;base>'s href value for resolving 
relative urls and a 
better 
detection of 
which 
images to keep in output.

These features I implemented in Readability4JExtended.

If you want to use it, simply instantiate with (the rest of the code stays the same):

<pre>
Readability4J readability4J = new <b>Readability4JExtended</b>(url, html);
Article article = readability4J.parse();
</pre>

## Output encoding

As users noted (see Issue [#1](https://github.com/dankito/Readability4J/issues/1) and [#2](https://github.com/dankito/Readability4J/issues/2))
by default no encoding is applied to Readability4J's output resulting in incorrect display of non-ASCII characters.

The reason is Readability returns its output in a &lt;div> element, and the only way to set the encoding in HTML is 
in a &lt;head>&lt;meta charset=""> tag.

So I added these convenience methods to Article class

```java
String contentHtmlWithUtf8Encoding = article.getContentWithUtf8Encoding();
// or
String contentHtmlWithCustomEncoding = article.getContentWithEncoding("ISO-8859-1");
```

which wrap the content in

```
<html>
 <head>
  <meta charset="utf-8" /> 
 </head>
 <body>
 <!-- content -->
 </body>
</html>
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
    <tr>
        <td>1.0.1</td>
        <td>834672e</td>
        <td>02/27/18</td>
    </tr>
</table>

## Extensibility

I tried to create the library as extensible as possible. All above mentioned classes can be overwritten and passed to Readability4J's constructor.

## Logging

Readability4J uses slf4j as logging facade.

So you can use any logger that supports slf4j, like Logback and log4j, to configure and get Readability4J's log output.

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