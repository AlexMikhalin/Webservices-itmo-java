package ifmo.webservices.j2ee;

import ifmo.webservices.Article;
import ifmo.webservices.ArticleFieldValue;
import ifmo.webservices.Field;
import ifmo.webservices.OracleSQLDAO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/Articles")
@Produces({MediaType.APPLICATION_JSON})
public class ArticleJ2EEResource {
    @GET
    public List<Article> getArticles(
            @QueryParam("author") String author,
            @QueryParam("id") int id,
            @QueryParam("name") String name,
            @QueryParam("pages") int pages,
            @QueryParam("publishing") String publishing,
            @QueryParam("year") int year) {

        OracleSQLDAO dao = new OracleSQLDAO(getConnection());
        List<ArticleFieldValue> conditions = new ArrayList<>();

        if (author != null) conditions.add(new ArticleFieldValue(Field.AUTHOR, author));
        if (id != 0) conditions.add(new ArticleFieldValue(Field.ID, id));
        if (name != null) conditions.add(new ArticleFieldValue(Field.NAME, name));
        if (pages != 0) conditions.add(new ArticleFieldValue(Field.PAGES, pages));
        if (publishing != null) conditions.add(new ArticleFieldValue(Field.PUBLISHING, publishing));
        if (year != 0) conditions.add(new ArticleFieldValue(Field.YEAR, year));

        return dao.getArticlesByFields(conditions);
    }

    private Connection getConnection() {
        Connection result = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource)ctx.lookup("ifmo-oracle");
            result = dataSource.getConnection();
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(ArticleJ2EEResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
