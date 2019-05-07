package ifmo.webservices;

import ifmo.webservices.errors.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "ArticleWebService", serviceName = "ArticleService")
public class ArticleWebServiceImpl implements ArticleWebService {

    @WebMethod(operationName = "getAllArticles")
    public List<Article> getAllArticles() throws DatabaseException {
        OracleSQLDAO dao = new OracleSQLDAO();
        try {
            return dao.getAllArticles();
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage(), ArticleServiceFault.defaultInstance());
        }
    }

    @WebMethod(operationName = "getArticles")
    public List<Article> getArticles(@WebParam(name = "conditions") List<ArticleFieldValue> conditions)
            throws DatabaseException {
        OracleSQLDAO dao = new OracleSQLDAO();
        try {
            return dao.getArticlesByFields(conditions);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage(), ArticleServiceFault.defaultInstance());
        }
    }

    @WebMethod(operationName = "addArticle")
    public int addArticle(@WebParam(name = "Article") Article Article)
            throws IllegalNameException,
            IllegalAuthorException,
            IllegalPagesException,
            IllegalYearException, DatabaseException {

        checkName(Article.getName());
        checkAuthor(Article.getAuthor());
        checkPages(Article.getPages());
        checkYear(Article.getYear());

        OracleSQLDAO dao = new OracleSQLDAO();
        try {
            return dao.addArticle(Article);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage(), ArticleServiceFault.defaultInstance());
        }
    }

    @WebMethod(operationName = "modifyArticle")
    public boolean modifyArticle(@WebParam(name = "id") int id, @WebParam(name = "newValues") List<ArticleFieldValue> newValues)
            throws IllegalNameException,
            IllegalAuthorException,
            IllegalPagesException,
            IllegalYearException,
            ArticleNotFoundException, DatabaseException {

        for (ArticleFieldValue fieldValue : newValues) {
            switch (fieldValue.getField()) {
                case NAME:
                    checkName(fieldValue.getValue().toString());
                    break;
                case AUTHOR:
                    checkAuthor(fieldValue.getValue().toString());
                    break;
                case PAGES:
                    checkPages(fieldValue.getValue().toString());
                    break;
                case YEAR:
                    checkYear(fieldValue.getValue().toString());
                    break;
            }
        }

        OracleSQLDAO dao = new OracleSQLDAO();
        try {
            checkExists(dao, id);
            return dao.modifyArticle(id, newValues);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage(), ArticleServiceFault.defaultInstance());
        }
    }

    @WebMethod(operationName = "deleteArticle")
    public boolean deleteArticle(@WebParam(name = "id") int id) throws ArticleNotFoundException, DatabaseException {
        OracleSQLDAO dao = new OracleSQLDAO();
        try {
            checkExists(dao, id);
            return dao.deleteArticle(id);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage(), ArticleServiceFault.defaultInstance());
        }
    }

    protected void checkExists(OracleSQLDAO dao, int id) throws ArticleNotFoundException, SQLException {
        if (dao.getArticlesByFields(new ArrayList<ArticleFieldValue>() {{
            add(new ArticleFieldValue(Field.ID, id));
        }}).size() == 0) {
            throw new ArticleNotFoundException("Article with such id is not found",
                    ArticleServiceFault.defaultInstance());
        }
    }

    protected void checkName(String name) throws IllegalNameException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalNameException("Article name is not specified",
                    ArticleServiceFault.defaultInstance());
        }
    }

    protected void checkAuthor(String author) throws IllegalAuthorException {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalAuthorException("Article author is not specified",
                    ArticleServiceFault.defaultInstance());
        }
    }

    protected void checkPages(String pages) throws IllegalPagesException {
        int pagesInt = -1;
        try {
           pagesInt = Integer.parseInt(pages);
        } catch (NumberFormatException e) {
            throw new IllegalPagesException("Pages should be number",
                    ArticleServiceFault.defaultInstance());
        }
       checkPages(pagesInt);
    }

    protected void checkYear(String year) throws IllegalYearException {
        int yearInt = -1;
        try {
            yearInt = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            throw new IllegalYearException("Years should be number",
                    ArticleServiceFault.defaultInstance());
        }
        checkYear(yearInt);
    }

    protected void checkPages(int pages) throws IllegalPagesException {
        if (pages <= 0) {
            throw new IllegalPagesException("Pages should be greater than zero",
                    ArticleServiceFault.defaultInstance());
        }
    }

    protected void checkYear(int year) throws IllegalYearException {
        if (year <= 0) {
            throw new IllegalYearException("Year should be greater than zero",
                    ArticleServiceFault.defaultInstance());
        }
    }
}