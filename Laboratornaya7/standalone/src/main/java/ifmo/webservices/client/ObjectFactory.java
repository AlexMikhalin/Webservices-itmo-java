
package ifmo.webservices.client;

import ifmo.webservices.Article;
import ifmo.webservices.ArticleFieldValue;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAllArticles_QNAME = new QName("http://webservices.ifmo/", "getAllArticles");
    private final static QName _GetAllArticlesResponse_QNAME = new QName("http://webservices.ifmo/", "getAllArticlesResponse");
    private final static QName _GetArticles_QNAME = new QName("http://webservices.ifmo/", "getArticles");
    private final static QName _GetArticlesResponse_QNAME = new QName("http://webservices.ifmo/", "getArticlesResponse");

    public ObjectFactory() {
    }

    public GetAllArticles createGetAllArticles() {
        return new GetAllArticles();
    }

    public GetAllArticlesResponse createGetAllArticlesResponse() {
        return new GetAllArticlesResponse();
    }

    public GetArticles createGetArticles() {
        return new GetArticles();
    }

    public GetArticlesResponse createGetArticlesResponse() {
        return new GetArticlesResponse();
    }

    public Article createArticle() {
        return new Article();
    }

    public ArticleFieldValue createArticleCondition() {
        return new ArticleFieldValue();
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "getAllArticles")
    public JAXBElement<GetAllArticles> createGetAllArticles(GetAllArticles value) {
        return new JAXBElement<GetAllArticles>(_GetAllArticles_QNAME, GetAllArticles.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "getAllArticlesResponse")
    public JAXBElement<GetAllArticlesResponse> createGetAllArticlesResponse(GetAllArticlesResponse value) {
        return new JAXBElement<GetAllArticlesResponse>(_GetAllArticlesResponse_QNAME, GetAllArticlesResponse.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "getArticles")
    public JAXBElement<GetArticles> createGetArticles(GetArticles value) {
        return new JAXBElement<GetArticles>(_GetArticles_QNAME, GetArticles.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "getArticlesResponse")
    public JAXBElement<GetArticlesResponse> createGetArticlesResponse(GetArticlesResponse value) {
        return new JAXBElement<GetArticlesResponse>(_GetArticlesResponse_QNAME, GetArticlesResponse.class, null, value);
    }

}
