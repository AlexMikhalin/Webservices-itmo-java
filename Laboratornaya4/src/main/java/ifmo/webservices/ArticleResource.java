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
}
