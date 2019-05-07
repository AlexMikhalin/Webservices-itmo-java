
package ifmo.webservices.client;

import ifmo.webservices.Article;
import ifmo.webservices.ArticleFieldValue;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _AddArticleResponse_QNAME = new QName("http://webservices.ifmo/", "addArticleResponse");
    private final static QName _DeleteArticleResponse_QNAME = new QName("http://webservices.ifmo/", "deleteArticleResponse");
    private final static QName _GetArticles_QNAME = new QName("http://webservices.ifmo/", "getArticles");
    private final static QName _DeleteArticle_QNAME = new QName("http://webservices.ifmo/", "deleteArticle");
    private final static QName _GetAllArticles_QNAME = new QName("http://webservices.ifmo/", "getAllArticles");
    private final static QName _ModifyArticleResponse_QNAME = new QName("http://webservices.ifmo/", "modifyArticleResponse");
    private final static QName _GetArticlesResponse_QNAME = new QName("http://webservices.ifmo/", "getArticlesResponse");
    private final static QName _ModifyArticle_QNAME = new QName("http://webservices.ifmo/", "modifyArticle");
    private final static QName _AddArticle_QNAME = new QName("http://webservices.ifmo/", "addArticle");
    private final static QName _GetAllArticlesResponse_QNAME = new QName("http://webservices.ifmo/", "getAllArticlesResponse");

    public ObjectFactory() {
    }

    public ModifyArticleResponse createModifyArticleResponse() {
        return new ModifyArticleResponse();
    }

    public DeleteArticle createDeleteArticle() {
        return new DeleteArticle();
    }

    public GetAllArticles createGetAllArticles() {
        return new GetAllArticles();
    }

    public GetArticles createGetArticles() {
        return new GetArticles();
    }

    public DeleteArticleResponse createDeleteArticleResponse() {
        return new DeleteArticleResponse();
    }

    public AddArticleResponse createAddArticleResponse() {
        return new AddArticleResponse();
    }

    public GetAllArticlesResponse createGetAllArticlesResponse() {
        return new GetAllArticlesResponse();
    }

    public AddArticle createAddArticle() {
        return new AddArticle();
    }

    public GetArticlesResponse createGetArticlesResponse() {
        return new GetArticlesResponse();
    }

    public ModifyArticle createModifyArticle() {
        return new ModifyArticle();
    }

    public Article createArticle() {
        return new Article();
    }

    public ArticleFieldValue createArticleCondition() {
        return new ArticleFieldValue();
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "addArticleResponse")
    public JAXBElement<AddArticleResponse> createAddArticleResponse(AddArticleResponse value) {
        return new JAXBElement<AddArticleResponse>(_AddArticleResponse_QNAME, AddArticleResponse.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "deleteArticleResponse")
    public JAXBElement<DeleteArticleResponse> createDeleteArticleResponse(DeleteArticleResponse value) {
        return new JAXBElement<DeleteArticleResponse>(_DeleteArticleResponse_QNAME, DeleteArticleResponse.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "getArticles")
    public JAXBElement<GetArticles> createGetArticles(GetArticles value) {
        return new JAXBElement<GetArticles>(_GetArticles_QNAME, GetArticles.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "deleteArticle")
    public JAXBElement<DeleteArticle> createDeleteArticle(DeleteArticle value) {
        return new JAXBElement<DeleteArticle>(_DeleteArticle_QNAME, DeleteArticle.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "getAllArticles")
    public JAXBElement<GetAllArticles> createGetAllArticles(GetAllArticles value) {
        return new JAXBElement<GetAllArticles>(_GetAllArticles_QNAME, GetAllArticles.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "modifyArticleResponse")
    public JAXBElement<ModifyArticleResponse> createModifyArticleResponse(ModifyArticleResponse value) {
        return new JAXBElement<ModifyArticleResponse>(_ModifyArticleResponse_QNAME, ModifyArticleResponse.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "getArticlesResponse")
    public JAXBElement<GetArticlesResponse> createGetArticlesResponse(GetArticlesResponse value) {
        return new JAXBElement<GetArticlesResponse>(_GetArticlesResponse_QNAME, GetArticlesResponse.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "modifyArticle")
    public JAXBElement<ModifyArticle> createModifyArticle(ModifyArticle value) {
        return new JAXBElement<ModifyArticle>(_ModifyArticle_QNAME, ModifyArticle.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "addArticle")
    public JAXBElement<AddArticle> createAddArticle(AddArticle value) {
        return new JAXBElement<AddArticle>(_AddArticle_QNAME, AddArticle.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "getAllArticlesResponse")
    public JAXBElement<GetAllArticlesResponse> createGetAllArticlesResponse(GetAllArticlesResponse value) {
        return new JAXBElement<GetAllArticlesResponse>(_GetAllArticlesResponse_QNAME, GetAllArticlesResponse.class, null, value);
    }

}
