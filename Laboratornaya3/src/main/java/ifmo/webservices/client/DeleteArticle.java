
package ifmo.webservices.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteArticle", propOrder = {
    "id"
})
public class DeleteArticle {

    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

}
