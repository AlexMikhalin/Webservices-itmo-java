package ifmo.webservices.client;

import ifmo.webservices.Article;
import ifmo.webservices.ArticleFieldValue;
import ifmo.webservices.ArticleService;
import ifmo.webservices.Field;
import ifmo.webservices.errors.DatabaseException;

import javax.xml.ws.WebServiceException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

enum MenuOption {AddField, Add, Delete, Modify, Print, Clear, Find, Exit}

public class WebServiceClient {

    private static final String standaloneUrl = "http://localhost:8081/ArticleService?wsdl";

    private String url;
    private ArticleService ArticleService;

    private List<ArticleFieldValue> conditions = new ArrayList<ArticleFieldValue>();

    public WebServiceClient(String serviceUrl) {
        this.url = serviceUrl;
    }

    public static void main(String[] args) {
        try {
            WebServiceClient client = new WebServiceClient(standaloneUrl);
            client.startListening();

        } catch (WebServiceException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void startListening() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            URL url = new URL(this.url);
            this.ArticleService = new ArticleService(url);

            while (true) {
                try {
                    printMenu();
                    processOption(in);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void printMenu() {
        System.out.println("------------Menu------------");

        MenuOption[] options = MenuOption.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%2d. %s\n", i + 1, getOptionText(options[i]));
        }
    }

    private int readIntValue(BufferedReader in) throws IOException {
        int option = -1;

        String input = in.readLine();
        try {
            option = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return option;
        }

        return option;
    }

    private void processOption(BufferedReader in) throws IOException, DatabaseException {
        int option = readIntValue(in);

        if (option < 1 || option > MenuOption.values().length) {
            System.err.println("Wrong option");
            return;
        }

        MenuOption menuOption = MenuOption.values()[option - 1];

        switch (menuOption) {
            case AddField:
                addCondition(in);
                break;
            case Add:
                add(in);
                break;
            case Delete:
                delete(in);
                break;
            case Modify:
                modify(in);
                break;
            case Find:
                findResults();
                break;
            case Print:
                printConditions();
                break;
            case Clear:
                clearConditions();
                break;
            case Exit:
                exit();
                break;
        }
    }

    private String getOptionText(MenuOption menuOption) {
        switch (menuOption) {
            case AddField:
                return "Add search condition";
            case Add:
                return "Add new Article";
            case Delete:
                return "Delete Article";
            case Modify:
                return "Modify Article";
            case Find:
                return "Find results";
            case Print:
                return "Print saved conditions";
            case Clear:
                return "Clear saved conditions";
            case Exit:
                return "Exit";
            default:
                return "Option not supported";
        }
    }

    private void addCondition(BufferedReader in) throws IOException {
        System.out.println("Choose field:");

        Field[] fields = Field.values();
        for (int i = 0; i < fields.length; i++) {
            System.out.printf("%2d. %s\n", i + 1, fields[i]);
        }

        int field = readIntValue(in);

        if (field < 1 || field > fields.length) {
            System.err.println("Wrong option");
            return;
        }

        System.out.println("Print expected field value:");
        String value = in.readLine();

        ArticleFieldValue condition = new ArticleFieldValue(fields[field - 1], value);
        this.conditions.add(condition);

        System.out.println("Condition saved: " + condition);
    }

    private void add(BufferedReader in) {
        Article Article = new Article();
        try {
            Field[] fields = Field.values();
            for (int i = 0; i < fields.length; i++) {
                if (fields[i] != Field.ID) {
                    System.out.printf("Print '%s' value:\n", fields[i]);
                    Article.setField(fields[i], in.readLine());
                }
            }

            int id = this.ArticleService.getArticleWebServicePort().addArticle(Article);
            System.out.println("Article added: id = " + id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void delete(BufferedReader in) {
        try {
            System.out.println("Print id:");
            int id = readIntValue(in);
            if (id < 0) {
                return;
            }

            boolean success = this.ArticleService.getArticleWebServicePort().deleteArticle(id);
            if (success) {
                System.out.println("Article deleted");
            } else {
                System.out.println("Article deletion failed");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void modify(BufferedReader in) {
        try {
            System.out.println("Print id:");
            int id = readIntValue(in);
            if (id < 0) {
                return;
            }

            List<ArticleFieldValue> newValues = new ArrayList<ArticleFieldValue>();

            Field[] fields = Field.values();

            for (int i = 0; i < fields.length; i++) {
                if (fields[i] != Field.ID) {
                    System.out.printf("Print '%s' value (to skip press enter):\n", fields[i]);
                    String value = in.readLine();

                    if (!value.isEmpty()) {
                        newValues.add(new ArticleFieldValue(fields[i], value));
                    }
                }
            }

            boolean success = this.ArticleService.getArticleWebServicePort().modifyArticle(id, newValues);
            if (success) {
                System.out.println("Article modified");
            } else {
                System.out.println("Article modification failed");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void findResults() throws DatabaseException {
        List<Article> Articles = this.ArticleService.getArticleWebServicePort().getArticles(this.conditions);
        printArticles(Articles);
    }

    private void printConditions() {
        if (this.conditions.size() == 0) {
            System.out.println("No conditions saved");
            return;
        }

        System.out.println("Saved conditions:");

        for (ArticleFieldValue condition : this.conditions) {
            System.out.println(condition);
        }
    }

    private void clearConditions() {
        this.conditions.clear();
        System.out.println("Saved conditions cleared");
    }

    private void exit() {
        System.exit(0);
    }

    private void printArticles(List<Article> Articles) {
        for (Article Article : Articles) {
            System.out.println(Article);
        }
        System.out.println("Total Articles: " + Articles.size());
    }
}