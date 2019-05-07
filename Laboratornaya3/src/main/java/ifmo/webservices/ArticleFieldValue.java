
package ifmo.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArticleCondition", propOrder = {
    "field", "value"
})
public class ArticleFieldValue {

    @XmlSchemaType(name = "string")
    protected Field field;

    public ArticleFieldValue(Field field, Object value) {
        this.field = field;
        this.value = value;
    }

    public ArticleFieldValue() {
    }

    @XmlSchemaType(name = "string")
    protected Object value;

    public Field getField() {
        return field;
    }

    public void setField(Field value) {
        this.field = value;
    }


    public Object getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Article filed value {" +
                "field=" + field +
                ", value='" + value +
                "'}";
    }
}
