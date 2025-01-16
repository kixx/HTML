package org.bsdro.http;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static  void main(String[] args )
    {
        String htmlString = "<html><body>Hello, <a href='Te<a>st'>World</a></body></html>";
        List<HTMLToken> tokens = HTMLTokenizer.parse(htmlString);
        for (HTMLToken token : tokens) {
            System.out.println(token.toString());
        }
    }
}
