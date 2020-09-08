package net.bsmch;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class Selectors {
    public static boolean isXPath(String xpathExpression) {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        try {
            xPath.compile(xpathExpression);
        } catch (XPathExpressionException ex) {
            return false;
        }
        return true;
    }
}
