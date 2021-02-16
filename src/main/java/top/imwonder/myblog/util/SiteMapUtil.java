/*
 * @Author: Wonder2020 
 * @Date: 2021-02-13 20:53:03 
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-02-13 21:31:49
 */
package top.imwonder.myblog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import top.imwonder.myblog.util.enumeration.ChangeFreqEnum;

public class SiteMapUtil {

    public static String xmlns = "http://www.sitemaps.org/schemas/sitemap/0.9";

    public static String xmlnsXsi = "http://www.w3.org/2001/XMLSchema-instance";

    public static String xsiSchemaLocation = "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd";

    public static Document createSiteMap() {
        Element urlset = new Element("urlset", xmlns);
        urlset.addNamespaceDeclaration("xsi", xmlnsXsi);
        urlset.addAttribute(new Attribute("xsi:schemaLocation", xmlnsXsi, SiteMapUtil.xsiSchemaLocation));
        Document doc = new Document(urlset);
        return doc;
    }

    public static Document addUrl(Document doc, String loc) {
        return addUrl(doc, loc, new Date(), ChangeFreqEnum.WEEKLY, "0.9", new SimpleDateFormat("yyyy-MM-dd"));
    }

    public static Document addUrl(Document doc, String loc, Date lastmod, ChangeFreqEnum changefreq, String priority) {
        return addUrl(doc, loc, lastmod, changefreq, priority, new SimpleDateFormat("yyyy-MM-dd"));
    }

    public static Document addUrl(Document doc, String loc, Date lastmod, ChangeFreqEnum changefreq, String priority, SimpleDateFormat sdf) {
        Element root = doc.getRootElement();
        Element url = new Element("url", root.getNamespaceURI());
        doc.getRootElement().appendChild(url);
        url.appendChild(addElement("loc", loc, url.getNamespaceURI()));
        url.appendChild(addElement("lastmod", sdf.format(lastmod), url.getNamespaceURI()));
        url.appendChild(addElement("changefreq", changefreq.getName(), url.getNamespaceURI()));
        url.appendChild(addElement("priority", priority, url.getNamespaceURI()));
        return doc;
    }

    private static Element addElement(String en, String content, String uri) {
        Element element = new Element(en, uri);
        element.appendChild(content);
        return element;
    }
}