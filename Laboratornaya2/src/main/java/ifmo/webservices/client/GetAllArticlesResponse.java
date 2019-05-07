
package ifmo.webservices.client;

import ifmo.webservices.Article;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAllArticlesResponse", propOrder = {
    "_return"
})
public class GetAllArticlesResponse {

    @XmlElement(name = "return")
    protected List<Article> _return;

    public List<Article> getReturn() {
        if (_return == null) {
            _return = new ArrayList<Article>();
        }
        return this._return;
    }

}
