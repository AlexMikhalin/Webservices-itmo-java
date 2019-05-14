package ifmo.webservices;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "ArticleWebService", serviceName = "ArticleService")
public class ArticleWebServiceImpl implements ArticleWebService {

    @WebMethod(operationName = "getAllArticles")
    public List<Article> getAllArticles() {
        OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
        List<Article> articles = dao.getAllArticles();
        return articles;
    }

    @WebMethod(operationName = "getArticles")
    public List<Article> getArticles(@WebParam(name = "conditions") List<ArticleFieldValue> conditions) {
        OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
        return dao.getArticlesByFields(conditions);
    }
}