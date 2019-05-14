
package ifmo.webservices.client;

import ifmo.webservices.ArticleFieldValue;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getArticles", propOrder = {
    "conditions"
})
public class GetArticles {

    protected List<ArticleFieldValue> conditions;

    public List<ArticleFieldValue> getConditions() {
        if (conditions == null) {
            conditions = new ArrayList<ArticleFieldValue>();
        }
        return this.conditions;
    }

}
