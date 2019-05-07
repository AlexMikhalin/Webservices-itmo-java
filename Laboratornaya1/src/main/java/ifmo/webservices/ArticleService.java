
package ifmo.webservices;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "ArticleService", targetNamespace = "http://webservices.ifmo/", wsdlLocation = "http://localhost:8081/ArticleService?wsdl")
public class ArticleService
    extends Service
{

    private final static URL ArticleSERVICE_WSDL_LOCATION;
    private final static WebServiceException ArticleSERVICE_EXCEPTION;
    private final static QName ArticleSERVICE_QNAME = new QName("http://webservices.ifmo/", "ArticleService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8081/ArticleService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ArticleSERVICE_WSDL_LOCATION = url;
        ArticleSERVICE_EXCEPTION = e;
    }

    public ArticleService() {
        super(__getWsdlLocation(), ArticleSERVICE_QNAME);
    }

    public ArticleService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ArticleSERVICE_QNAME, features);
    }

    public ArticleService(URL wsdlLocation) {
        super(wsdlLocation, ArticleSERVICE_QNAME);
    }

    public ArticleService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ArticleSERVICE_QNAME, features);
    }

    public ArticleService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ArticleService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name = "ArticleWebServicePort")
    public ArticleWebService getArticleWebServicePort() {
        return super.getPort(new QName("http://webservices.ifmo/", "ArticleWebServicePort"), ArticleWebService.class);
    }

    @WebEndpoint(name = "ArticleWebServicePort")
    public ArticleWebService getArticleWebServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://webservices.ifmo/", "ArticleWebServicePort"), ArticleWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ArticleSERVICE_EXCEPTION!= null) {
            throw ArticleSERVICE_EXCEPTION;
        }
        return ArticleSERVICE_WSDL_LOCATION;
    }

}
