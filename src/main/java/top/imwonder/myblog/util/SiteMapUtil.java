package top.imwonder.myblog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import top.imwonder.myblog.util.enumeration.ChangeFreqEnum;

public class SiteMapUtil {

    public static Document createSiteMap() {
        String xmlnsXsi = "http://www.w3.org/2001/XMLSchema-instance";
        String xmlns = "http://www.sitemaps.org/schemas/sitemap/0.9";
        String xsiSchemaLocation = "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd";
        Document doc = DocumentHelper.createDocument();
        Element urlset = doc.addElement("urlset", xmlns);
        urlset.addAttribute("xmlns:xsi", xmlnsXsi);
        urlset.addAttribute("xsi:schemaLocation", xsiSchemaLocation);
        return doc;
    }

    public static Document addUrl(Document doc, String loc) {
        return addUrl(doc, loc, new Date(), ChangeFreqEnum.WEEKLY, "0.9", new SimpleDateFormat("yyyy-MM-dd"));
    }

    public static Document addUrl(Document doc, String loc, Date lastmod, ChangeFreqEnum changefreq, String priority) {
        return addUrl(doc, loc, lastmod, changefreq, priority, new SimpleDateFormat("yyyy-MM-dd"));
    }

    public static Document addUrl(Document doc, String loc, Date lastmod, ChangeFreqEnum changefreq, String priority, SimpleDateFormat sdf) {
        Element url = doc.getRootElement().addElement("url");
        url.addElement("loc").addCDATA(loc);
        url.addElement("lastmod").setText(sdf.format(lastmod));
        url.addElement("changefreq").setText(changefreq.getName());
        url.addElement("priority").setText(priority);
        return doc;
    }
}