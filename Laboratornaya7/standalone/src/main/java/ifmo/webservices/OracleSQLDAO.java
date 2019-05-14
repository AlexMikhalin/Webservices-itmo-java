package ifmo.webservices;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OracleSQLDAO {
    private Connection connection;

    public OracleSQLDAO(Connection connection) {
        this.connection = connection;
    }

    private List<Article> getArticles(String query) {
        List<Article> articles = new ArrayList<Article>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt(Field.fromValue("id").toString());
                String name = rs.getString(Field.fromValue("name").toString());
                String publishing = rs.getString(Field.fromValue("publishing").toString());
                String author = rs.getString(Field.fromValue("author").toString());
                int year = rs.getInt(Field.fromValue("year").toString());
                int pages = rs.getInt(Field.fromValue("pages").toString());

                Article article = new Article(author, id, name, pages, publishing, year);
                articles.add(article);
            }
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articles;
    }

    public List<Article> getAllArticles() {
        return getArticles("select * from Articles");
    }

    public List<Article> getArticlesByFields(List<ArticleFieldValue> ArticleRequests) {
        StringBuilder query = new StringBuilder("select * from Articles where ");

        for (ArticleFieldValue ArticleRequest : ArticleRequests) {
            String equalExpression = String.format("%s = '%s'", ArticleRequest.getField(), ArticleRequest.getValue());
            query.append(equalExpression);

            if(!ArticleRequest.equals(ArticleRequests.get(ArticleRequests.size() - 1))) {
                query.append(" and ");
            }
        }
        return getArticles(query.toString());
    }
}