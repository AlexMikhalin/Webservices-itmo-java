package ifmo.webservices.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import ifmo.webservices.Article;
import ifmo.webservices.ArticleFieldValue;
import ifmo.webservices.Field;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

enum MenuOption {Add, Print, Clear, Find, Exit}

public class WebServiceClient {

    private static final String standaloneUrl = "http://localhost:8081/rest/Articles";
    private static final String j2eeUrl = "http://localhost:8082/WebService1-1.0-SNAPSHOT/rest/Articles";

    private Client client;
    private String url;

    private List<ArticleFieldValue> conditions = new ArrayList<>();

    public WebServiceClient(Client client, String url) {
        this.client = client;
        this.url = url;
    }

    public static void main(String[] args) throws MalformedURLException {
        try {
            WebServiceClient client = new WebServiceClient(Client.create(), standaloneUrl);
            client.startListening();

        } catch (WebServiceException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void startListening() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                try {
                    printMenu();
                    processOption(in);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }

        } catch (MalformedURLException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
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

    private int readOption(BufferedReader in) throws IOException {
        int option = -1;

        String input = in.readLine();
        try {
            option = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.err.println("Wrong option");
        }

        return option;
    }

    private void processOption(BufferedReader in) throws IOException {
        int option = readOption(in);

        if (option < 1 || option > MenuOption.values().length) {
            System.err.println("Wrong option");
            return;
        }

        MenuOption menuOption = MenuOption.values()[option - 1];

        switch (menuOption) {
            case Add:
                addCondition(in);
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
            case Add:
                return "Add search condition";
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

        int field = readOption(in);

        if (field < 1 || field > fields.length) {
            System.err.println("Wrong option");
        }

        System.out.println("Print expected field value:");
        String value = in.readLine();

        ArticleFieldValue condition = new ArticleFieldValue(fields[field - 1], value);
        this.conditions.add(condition);

        System.out.println("Condition saved: " + condition);
    }

    private void findResults() {
        WebResource webResource = client.resource(this.url);
        for (ArticleFieldValue condition : conditions) {
            webResource = webResource.queryParam(
                    condition.getField().toString(),
                    condition.getValue().toString());
        }

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }

        GenericType<List<Article>> type = new GenericType<List<Article>>() {};
        printArticles(response.getEntity(type));
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