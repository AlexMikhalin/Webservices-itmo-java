package ifmo.webservices;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/Articles")
@Produces({MediaType.APPLICATION_JSON})
public class ArticleResource {
    @GET
    public List<Article> getArticles(
            @QueryParam("author") String author,
            @QueryParam("id") int id,
            @QueryParam("name") String name,
            @QueryParam("pages") int pages,
            @QueryParam("publishing") String publishing,
            @QueryParam("year") int year) {

        OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
        List<ArticleFieldValue> conditions = new ArrayList<>();

        if (author != null) conditions.add(new ArticleFieldValue(Field.AUTHOR, author));
        if (id != 0) conditions.add(new ArticleFieldValue(Field.ID, id));
        if (name != null) conditions.add(new ArticleFieldValue(Field.NAME, name));
        if (pages != 0) conditions.add(new ArticleFieldValue(Field.PAGES, pages));
        if (publishing != null) conditions.add(new ArticleFieldValue(Field.PUBLISHING, publishing));
        if (year != 0) conditions.add(new ArticleFieldValue(Field.YEAR, year));

        return dao.getArticlesByFields(conditions);
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public int addArticle(Article Article) {
        OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
        return dao.addArticle(Article);
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    public boolean modifyArticle(Article Article) {
        OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());

        List<ArticleFieldValue> newValues = new ArrayList<>();

        if(Article.getAuthor() != null) newValues.add(new ArticleFieldValue(Field.AUTHOR, Article.getAuthor()));
        if(Article.getName() != null) newValues.add(new ArticleFieldValue(Field.NAME, Article.getName()));
        if(Article.getPublishing() != null) newValues.add(new ArticleFieldValue(Field.PUBLISHING, Article.getPublishing()));
        if(Article.getPages() > 0) newValues.add(new ArticleFieldValue(Field.PAGES, Article.getPages()));
        if(Article.getYear() > 0) newValues.add(new ArticleFieldValue(Field.YEAR, Article.getYear()));

        return dao.modifyArticle(Article.id, newValues);
    }

    @DELETE
    public boolean deleteArticle(@QueryParam("id") int id) {
        OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
        return dao.deleteArticle(id);
    }
}
