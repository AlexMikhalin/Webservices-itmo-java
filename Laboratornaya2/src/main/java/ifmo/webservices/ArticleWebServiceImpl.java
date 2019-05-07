package ifmo.webservices;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "ArticleWebService", serviceName = "ArticleService")
public class ArticleWebServiceImpl implements ArticleWebService {

    @WebMethod(operationName = "getAllArticles")
    public List<Article> getAllArticles() {
        OracleSQLDAO dao = new OracleSQLDAO();
        List<Article> Articles = dao.getAllArticles();
        return Articles;
    }

    @WebMethod(operationName = "getArticles")
    public List<Article> getArticles(@WebParam(name = "conditions") List<ArticleFieldValue> conditions) {
        OracleSQLDAO dao = new OracleSQLDAO();
        return dao.getArticlesByFields(conditions);
    }

    @WebMethod(operationName = "addArticle")
    public int addArticle(@WebParam(name = "Article") Article Article) {
        OracleSQLDAO dao = new OracleSQLDAO();
        return dao.addArticle(Article);
    }

    @WebMethod(operationName = "modifyArticle")
    public boolean modifyArticle(@WebParam(name = "id") int id, @WebParam(name = "newValues") List<ArticleFieldValue> newValues) {
        OracleSQLDAO dao = new OracleSQLDAO();
        return dao.modifyArticle(id, newValues);
    }

    @WebMethod(operationName = "deleteArticle")
    public boolean deleteArticle(@WebParam(name = "id") int id) {
        OracleSQLDAO dao = new OracleSQLDAO();
        return dao.deleteArticle(id);
    }
}