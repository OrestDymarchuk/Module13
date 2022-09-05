package FirstTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class HttpUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();


    /*
     * First task methods
     */

    // Task 1.1 method, create user
    public static int sendPost(URI uri, User user) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        GSON.fromJson(response.body(), User.class);

        return response.statusCode();
    }

    // Task 1.2 method, update user
    public static int sendPut(URI uri, int userId, User user) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + userId))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        GSON.fromJson(response.body(), User.class);
        return response.statusCode();
    }

    // Task 1.3 method, delete user
    public static int sendDelete(URI uri, int userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + userId))
                .DELETE()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        GSON.fromJson(response.body(), User.class);
        return response.statusCode();
    }

    // Task 1.4 method, get all users
    public static List<User> sendGetUsers(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
    }

    // Task 1.5 method, get user by his id
    public static User sendGetByUserID(URI uri, int userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + userId))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    // Task 1.6 method, get user by his username
    public static User sendGetByUsername(URI uri, String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "?username=" + username))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<User> getUserByUserName = GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
        return getUserByUserName.get(0);
    }


    /*
     * Second task methods
     */

    // Task 2.1 method, get highest post id
    public static int sendGetHighestPostId(URI uri, int taskTwoUserId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + taskTwoUserId + "/posts"))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<Posts> posts = GSON.fromJson(response.body(), new TypeToken<List<Posts>>() {
        }.getType());

        return posts.stream()
                .map(Posts::getId)
                .max(Integer::compare)
                .orElse(1);
    }


    // Task 2.2 method, get posts with the highest post id
    public static List<Comments> sendGetUserComments(URI uri, int highestPostId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + highestPostId + "/comments"))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<Comments>>() {
        }.getType());
    }


    // Task 2.3 method, write comments to the Json
    public static void writeCommentsToJson(List<Comments> comments, int taskTwoUserId, int highestPostId) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("target/user-" + taskTwoUserId + "-post-" + highestPostId + "-comments.json"))) {
            writer.write(GSON.toJson(comments));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    /*
     * Third task methods
     */

    // Task 3 method, get user's todos with status false
    public static List<Todos> sendGetUserTodos(URI uri, int taskThreeUserId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + taskThreeUserId + "/todos"))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        List<Todos> todo = GSON.fromJson(response.body(), new TypeToken<List<Todos>>() {
        }.getType());

        return todo.stream()
                .filter(el -> !el.isCompleted())
                .collect(Collectors.toList());

    }
}
