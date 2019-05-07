
package ifmo.webservices.client;

import ifmo.webservices.ArticleFieldValue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyArticle", propOrder = {
    "id",
    "newValues"
})
public class ModifyArticle {

    protected int id;
    protected List<ArticleFieldValue> newValues;

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public List<ArticleFieldValue> getArticleFieldValues() {
        if (newValues == null) {
            newValues = new ArrayList<ArticleFieldValue>();
        }
        return this.newValues;
    }

}
