
package ifmo.webservices;

import ifmo.webservices.client.ObjectFactory;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


@WebService(name = "ArticleWebService", targetNamespace = "http://webservices.ifmo/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ArticleWebService {

    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAllArticles", targetNamespace = "http://webservices.ifmo/", className = "ifmo.webservices.client.GetAllArticles")
    @ResponseWrapper(localName = "getAllArticlesResponse", targetNamespace = "http://webservices.ifmo/", className = "ifmo.webservices.client.GetAllArticlesResponse")
    public List<Article> getAllArticles();

    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getArticles", targetNamespace = "http://webservices.ifmo/", className = "ifmo.webservices.client.GetArticles")
    @ResponseWrapper(localName = "getArticlesResponse", targetNamespace = "http://webservices.ifmo/", className = "ifmo.webservices.client.GetArticlesResponse")
    public List<Article> getArticles(
            @WebParam(name = "conditions", targetNamespace = "")
                    List<ArticleFieldValue> conditions);

    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "modifyArticle", targetNamespace = "http://webservices.ifmo/", className = "ifmo.webservices.client.ModifyArticle")
    @ResponseWrapper(localName = "modifyArticleResponse", targetNamespace = "http://webservices.ifmo/", className = "ifmo.webservices.client.ModifyArticleResponse")
    public boolean modifyArticle(
            @WebParam(name = "id", targetNamespace = "")
                    int id,
            @WebParam(name = "newValues", targetNamespace = "")
                    List<ArticleFieldValue> newValues);

    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addArticle", targetNamespace = "http://webservices.ifmo/", className = "ifmo.webservices.client.AddArticle")
    @ResponseWrapper(localName = "addArticleResponse", targetNamespace = "http://webservices.ifmo/", className = "ifmo.webservices.client.AddArticleResponse")
    public int addArticle(
            @WebParam(name = "Article", targetNamespace = "")
                    Article Article);

    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteArticle", targetNamespace = "http://webservices.ifmo/", className = "ifmo.webservices.client.DeleteArticle")
    @ResponseWrapper(localName = "deleteArticleResponse", targetNamespace = "http://webservices.ifmo/", className = "ifmo.webservices.client.DeleteArticleResponse")
    public boolean deleteArticle(
            @WebParam(name = "id", targetNamespace = "")
                    int id);

}
