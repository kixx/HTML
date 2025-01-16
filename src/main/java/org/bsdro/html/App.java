package org.bsdro.html;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static  void main(String[] args )
    {
        String htmlString = "<html><body>" +
                "Hello, <a href='Test'>World</a><br>" +
                "<a href=\"/hello\">Hello</a>, world<br>" +
                "<a href=\"http://example.com/helloWorld\">Hello, World</a><br>" +
                "</body></html>";
        List<HTMLToken> tokens = HTMLTokenizer.parse(htmlString);
        String[] links = HTMLTokenizer.filterLinks(tokens, "example.com");
        for (String link : links) {
            System.out.println(link);
        }
    }
}
