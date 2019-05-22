package ifmo.webservices;

import com.sun.jersey.multipart.FormDataParam;
import ifmo.webservices.errors.*;

import ifmo.webservices.errors.ForbiddenException;
import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("/Articles")
@Produces({MediaType.APPLICATION_JSON})
public class ArticleResource {
	
    private static final String login = "hardcode123";
    private static final String password = "hardcode123";
	
    @GET
    public List<Article> getArticles(
            @QueryParam("author") String author,
            @QueryParam("id") int id,
            @QueryParam("name") String name,
            @QueryParam("pages") int pages,
            @QueryParam("publishing") String publishing,
            @QueryParam("year") int year) throws DatabaseException {

        try {
            OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
            List<ArticleFieldValue> conditions = new ArrayList<>();

            if (author != null) conditions.add(new ArticleFieldValue(Field.AUTHOR, author));
            if (id != 0) conditions.add(new ArticleFieldValue(Field.ID, id));
            if (name != null) conditions.add(new ArticleFieldValue(Field.NAME, name));
            if (pages != 0) conditions.add(new ArticleFieldValue(Field.PAGES, pages));
            if (publishing != null) conditions.add(new ArticleFieldValue(Field.PUBLISHING, publishing));
            if (year != 0) conditions.add(new ArticleFieldValue(Field.YEAR, year));

            return dao.getArticlesByFields(conditions);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public int addArticle(Article Article)
            throws IllegalNameException,
            IllegalAuthorException,
            IllegalPagesException,
            IllegalYearException, DatabaseException {
        try {
            checkName(Article.getName());
            checkAuthor(Article.getAuthor());
            checkPages(Article.getPages());
            checkYear(Article.getYear());

            OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
            return dao.addArticle(Article);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public boolean modifyArticle(Article Article)
            throws IllegalNameException,
            IllegalAuthorException,
            IllegalPagesException,
            IllegalYearException,
            ArticleNotFoundException, DatabaseException {

        try {
            checkName(Article.getName());
            checkAuthor(Article.getAuthor());
            checkPages(Article.getPages());
            checkYear(Article.getYear());

            OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
            checkExists(dao, Article.getId());

            List<ArticleFieldValue> newValues = new ArrayList<>();

            if (Article.getAuthor() != null) newValues.add(new ArticleFieldValue(Field.AUTHOR, Article.getAuthor()));
            if (Article.getName() != null) newValues.add(new ArticleFieldValue(Field.NAME, Article.getName()));
            if (Article.getPublishing() != null) newValues.add(new ArticleFieldValue(Field.PUBLISHING, Article.getPublishing()));
            if (Article.getPages() > 0) newValues.add(new ArticleFieldValue(Field.PAGES, Article.getPages()));
            if (Article.getYear() > 0) newValues.add(new ArticleFieldValue(Field.YEAR, Article.getYear()));

            return dao.modifyArticle(Article.id, newValues);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @POST
    @Path("/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public boolean uploadFileWithData(
            @FormDataParam("file") InputStream fileInputStream,
            @HeaderParam("authorization") String authString) throws UnauthorizedException, ForbiddenException {

        checkAuthenticated(authString);

        try {
            File file = new File("src/main/resources/" +
                    new SimpleDateFormat("ddMMyy-hhmmss.SSS").format(new Date()));

            Files.copy(fileInputStream, file.toPath());
        } catch (IOException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
			return false;
        }
        return true;
    }


    @DELETE
    public boolean deleteArticle(@QueryParam("id") int id) throws ArticleNotFoundException, DatabaseException {
        try {
            OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
            checkExists(dao, id);
            return dao.deleteArticle(id);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage());
        }
    }

    protected void checkExists(OracleSQLDAO dao, final int id) throws ArticleNotFoundException, SQLException {
        if (dao.getArticlesByFields(new ArrayList<ArticleFieldValue>() {{
            add(new ArticleFieldValue(Field.ID, id));
        }}).size() == 0) {
            throw new ArticleNotFoundException("Article with such id is not found");
        }
    }

    protected void checkName(String name) throws IllegalNameException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalNameException("Article name is not specified");
        }
    }

    protected void checkAuthor(String author) throws IllegalAuthorException {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalAuthorException("Article author is not specified");
        }
    }

    protected void checkPages(int pages) throws IllegalPagesException {
        if (pages <= 0) {
            throw new IllegalPagesException("Pages should be greater than zero");
        }
    }

    protected void checkYear(int year) throws IllegalYearException {
        if (year <= 0) {
            throw new IllegalYearException("Year should be greater than zero");
        }
    }
}
