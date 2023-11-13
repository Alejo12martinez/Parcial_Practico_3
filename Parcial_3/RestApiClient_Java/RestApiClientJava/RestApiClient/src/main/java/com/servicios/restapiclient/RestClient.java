package com.servicios.restapiclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class RestClient {

    private static final String BASE_URL = "http://192.168.60.3:5000/books/";

    private static final String BASE_URL_POST = "http://192.168.60.3:5000/books";

    public Book sendGetRequest(int bookId) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(BASE_URL + bookId);
            HttpResponse response = httpClient.execute(httpGet);

            // Verificar el código de respuesta antes de procesar el cuerpo de la respuesta
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Extraer el cuerpo de la respuesta como una cadena
                String jsonResponse = EntityUtils.toString(response.getEntity());

                // Llamar al método parseResponse con la cadena extraída
                return parseResponse(jsonResponse);
            } else {
                System.err.println("Error en la solicitud. Código de respuesta: " + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Book parseResponse(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            // Verificar si el JSON contiene la propiedad "book"
            if (root.has("book")) {
                // Obtener el objeto JSON bajo la propiedad "book"
                JsonNode bookNode = root.get("book");

                // Deserializar el objeto "book"
                return objectMapper.treeToValue(bookNode, Book.class);
            } else {
                // Si no hay una propiedad "book", intentar deserializar directamente
                return objectMapper.readValue(jsonResponse, Book.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Book> parseResponseList(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            // Verificar si el JSON contiene la propiedad "books"
            if (root.has("books")) {
                // Obtener el array JSON bajo la propiedad "books"
                JsonNode booksNode = root.get("books");

                // Deserializar el array "books" a una lista de objetos Book
                return objectMapper.readValue(booksNode.toString(), new TypeReference<List<Book>>() {
                });
            } else {
                // Si no hay una propiedad "books", intentar deserializar directamente
                return Collections.singletonList(objectMapper.readValue(jsonResponse, Book.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Book> sendGetAllBooks() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(BASE_URL_POST);
            HttpResponse response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Extraer el cuerpo de la respuesta como una cadena
                String jsonResponse = EntityUtils.toString(response.getEntity());

                // Llamar al método parseResponse con la cadena extraída
                return (List<Book>) parseResponseList(jsonResponse);
            } else {
                System.err.println("Error en la solicitud. Código de respuesta: " + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendPostRequest(String jsonBody) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(BASE_URL_POST);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonBody));

            HttpResponse response = httpClient.execute(httpPost);

            printResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPutRequest(int bookId, Book updatedBook) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(BASE_URL + bookId);
            HttpResponse response = httpClient.execute(httpGet);

            // Verificar si el código de respuesta es 200 (OK) antes de procesar el cuerpo de la respuesta
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Si el libro existe, enviar la solicitud PUT para actualizar el libro
                HttpPut httpPut = new HttpPut(BASE_URL + bookId);
                httpPut.setHeader("Content-Type", "application/json");

                // Convertir el objeto Book a JSON y establecerlo como el cuerpo de la solicitud
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonBody = objectMapper.writeValueAsString(updatedBook);
                httpPut.setEntity(new StringEntity(jsonBody));

                response = httpClient.execute(httpPut);

                printResponse(response);
            } else {
                // Si el libro no existe, imprimir un mensaje
                System.err.println("Book with ID " + bookId + " does not exist. Cannot update.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendDeleteRequest(int bookId) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(BASE_URL + bookId);
            HttpResponse response = httpClient.execute(httpGet);

            // Verificar si el código de respuesta es 200 (OK) antes de procesar el cuerpo de la respuesta
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Si el libro existe, enviar la solicitud DELETE
                HttpDelete httpDelete = new HttpDelete(BASE_URL + bookId);
                response = httpClient.execute(httpDelete);

                printResponse(response);
            } else {
                // Si el libro no existe, imprimir un mensaje
                System.err.println("Book with ID " + bookId + " does not exist. Cannot delete.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void printResponse(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }
    }
}
