package FirstTask;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class Tasks {
    private static final String USERS = "https://jsonplaceholder.typicode.com/users";
    private static final String COMMENT = "https://jsonplaceholder.typicode.com/posts";

    public static void firstTask() {
        User user = UserCreator.createUser();
        int userId = 9;
        String username = "Moriah.Stanton";

        // Task 1.1 Create User
        int firstTaskStatusCode = 0;
        try {
            firstTaskStatusCode = HttpUtil.sendPost(URI.create(USERS), user);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("1.1 task status code is " + firstTaskStatusCode);

        // Task 1.2 Update User
        int secondTaskStatusCode = 0;
        try {
            secondTaskStatusCode = HttpUtil.sendPut(URI.create(USERS), userId, user);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("1.2 task status code is " + secondTaskStatusCode);

        // Task 1.3 Delete User
        int thirdTaskStatusCode = 0;
        try {
            thirdTaskStatusCode = HttpUtil.sendDelete(URI.create(USERS), userId);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("1.3 task status code is " + thirdTaskStatusCode);

        // Task 1.4 Get All Users
        List<User> userList = null;
        try {
            userList = HttpUtil.sendGetUsers(URI.create(USERS));
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("The result of 1.4 task is:");
        if (userList != null) {
            userList.forEach(System.out::println);
        } else {
            System.out.println("Users list is empty");
        }

        // Task 1.5 Get User By User Id
        User userById = null;
        try {
            userById = HttpUtil.sendGetByUserID(URI.create(USERS), userId);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("The result of 1.5 task is:");
        if (userById != null) {
            System.out.println(userById);
        } else {
            System.out.println("User is empty");
        }

        // Task 1.6 Get User By Username
        User getUserByUsername = null;
        try {
            getUserByUsername = HttpUtil.sendGetByUsername(URI.create(USERS), username);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("The result of 1.6 task is:");
        if (getUserByUsername != null) {
            System.out.println(getUserByUsername);
        } else {
            System.out.println("User is empty");
        }
    }

    public static void secondTask() {
        int userId = 1;
        int highestPostId = 0;

        // Getting The Highest Post ID
        try {
            highestPostId = HttpUtil.sendGetHighestPostId(URI.create(USERS), userId);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }

        List<Comments> comments = null;

        // Getting Comments With The Highest Post ID
        if (highestPostId != 0) {
            try {
                comments = HttpUtil.sendGetUserComments(URI.create(COMMENT), highestPostId);
            } catch (IOException | InterruptedException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("There are no posts");
        }

        // Writing Comments To The Json file

        if (comments != null) {
            HttpUtil.writeCommentsToJson(comments, userId, highestPostId);
            System.out.println("The second task is completed, the Json file is created in the Target folder)");
        } else {
            System.out.println("There are no comments");
        }
    }

    public static void thirdTask() {
        int userId = 1;
        try {
            System.out.println("Uncompleted tasks for the third task are:");
            HttpUtil.sendGetUserTodos(URI.create(USERS), userId).forEach(System.out::println);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
