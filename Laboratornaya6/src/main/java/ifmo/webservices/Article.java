
package ifmo.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Article", propOrder = {
        "author",
        "id",
        "name",
        "pages",
        "publishing",
        "year"
})
public class Article {
    public Article(String author, int id, String name, int pages, String publishing, int year) {
        this.author = author;
        this.id = id;
        this.name = name;
        this.pages = pages;
        this.publishing = publishing;
        this.year = year;
    }

    public Article() {

    }

    @XmlElement(name = "author", required = true)
    protected String author;

    @XmlElement(name = "id", required = true)
    protected int id;

    @XmlElement(name = "name", required = true)
    protected String name;

    @XmlElement(name = "pages", required = true)
    protected int pages;

    @XmlElement(name = "publishing", required = true)
    protected String publishing;

    @XmlElement(name = "year", required = true)
    protected int year;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String value) {
        this.author = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int value) {
        this.pages = value;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String value) {
        this.publishing = value;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int value) {
        this.year = value;
    }
	
    public void setField(Field field, String value) {
        switch (field) {
            case NAME: setName(value); break;
            case PUBLISHING: setPublishing(value); break;
            case AUTHOR: setAuthor(value); break;
            case YEAR: setYear(Integer.parseInt(value)); break;
            case PAGES: setPages(Integer.parseInt(value)); break;
        }
    }

    @Override
    public String toString() {
        return "Article {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", publishing='" + publishing + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", pages=" + pages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article Article = (Article) o;
        return id == Article.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
