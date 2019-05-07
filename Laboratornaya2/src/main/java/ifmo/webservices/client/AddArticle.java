
package ifmo.webservices.client;

import ifmo.webservices.Article;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addArticle", propOrder = {
    "Article"
})
public class AddArticle {

    protected Article Article;

    public Article getArticle() {
        return Article;
    }


    public void setArticle(Article value) {
        this.Article = value;
    }

}
