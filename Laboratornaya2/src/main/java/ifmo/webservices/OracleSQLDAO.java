package ifmo.webservices;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OracleSQLDAO {
    private List<Article> getArticles(String query) {
        List<Article> Articles = new ArrayList<Article>();

        try (Connection connection = ConnectionUtil.getConnection()) {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt(Field.fromValue("id").toString());
                String name = rs.getString(Field.fromValue("name").toString());
                String publishing = rs.getString(Field.fromValue("publishing").toString());
                String author = rs.getString(Field.fromValue("author").toString());
                int year = rs.getInt(Field.fromValue("year").toString());
                int pages = rs.getInt(Field.fromValue("pages").toString());

                Article Article = new Article(author, id, name, pages, publishing, year);
                Articles.add(Article);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Articles;
    }

    public List<Article> getAllArticles() {
        return getArticles("select * from Articles");
    }

    public List<Article> getArticlesByFields(List<ArticleFieldValue> ArticleRequests) {
        if(ArticleRequests.size() == 0) return getAllArticles();

        StringBuilder query = new StringBuilder("select * from Articles where ");

        for (ArticleFieldValue ArticleRequest : ArticleRequests) {
            String equalExpression = String.format("%s = '%s'", ArticleRequest.getField(), ArticleRequest.getValue());
            query.append(equalExpression);

            if (!ArticleRequest.equals(ArticleRequests.get(ArticleRequests.size() - 1))) {
                query.append(" and ");
            }
        }
        return getArticles(query.toString());
    }

    public int addArticle(Article Article) {
        int id = -1;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement(String.format(
                    "insert into Articles(name, publishing, author, year, pages) " +
                            "values('%s', '%s', '%s', %5d, %5d)",
                    Article.getName(),
                    Article.getPublishing(),
                    Article.getAuthor(),
                    Article.getYear(),
                    Article.getPages()),
                    new String[]{"id"});

            int i = pstm.executeUpdate();
            if (i > 0) {
                ResultSet rs = pstm.getGeneratedKeys();
                while (rs.next()) {
                    id = Integer.parseInt(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public boolean modifyArticle(int id, List<ArticleFieldValue> newValues) {
        if (newValues.size() == 0) {
            return false;
        }

        StringBuilder query = new StringBuilder("update Articles set ");

        for (ArticleFieldValue ArticleRequest : newValues) {
            String equalExpression = String.format("%s = '%s'", ArticleRequest.getField(), ArticleRequest.getValue());
            query.append(equalExpression);

            if (!ArticleRequest.equals(newValues.get(newValues.size() - 1))) {
                query.append(", ");
            } else {
                query.append("where id = ");
                query.append(id);
            }
        }

        return executeOperation(query.toString());
    }

    public boolean deleteArticle(int id) {
        String query = String.format("delete from Articles where id = %5d", id);
        return executeOperation(query);
    }

    private boolean executeOperation(String query) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}