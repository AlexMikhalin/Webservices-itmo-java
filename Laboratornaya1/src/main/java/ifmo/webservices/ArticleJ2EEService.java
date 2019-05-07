package ifmo.webservices;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@WebService(name = "ArticleWebService", serviceName = "ArticleService")
public class ArticleJ2EEService implements ArticleWebService {
    @WebMethod(operationName = "getAllArticles")
    public List<Article> getAllArticles() {
        OracleSQLDAO dao = new OracleSQLDAO(getConnection());
        List<Article> articles = dao.getAllArticles();
        return articles;
    }

    @WebMethod(operationName = "getArticles")
    public List<Article> getArticles(@WebParam(name = "conditions") List<ArticleFieldValue> conditions) {
        OracleSQLDAO dao = new OracleSQLDAO(getConnection());
        return dao.getArticlesByFields(conditions);
    }

    private Connection getConnection() {
        Connection result = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource)ctx.lookup("ifmo-oracle");
            result = dataSource.getConnection();
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(ArticleJ2EEService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
