package FirstTask;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class Main {
    private static final String USERS = "https://jsonplaceholder.typicode.com/users";

    public static void main(String[] args) throws IOException, InterruptedException {
        User user = createUser();

        // Task 1.1 Create
        User newUser = HttpUtil.sendPost(URI.create(USERS), user);
        System.out.println(newUser);

        // Task 1.2 Update
        newUser.setName("Michael");
        User updatedUser = HttpUtil.sendPut(URI.create(USERS), newUser);
        System.out.println(updatedUser);

        // Task 1.3 Delete
        User deletedUser = HttpUtil.sendDelete(URI.create(USERS), 8);
        System.out.println(deletedUser);

        // Task 1.4 Get All Users
        List<User> userList = HttpUtil.sendGetUsers(URI.create(USERS));
        userList.forEach(System.out::println);

        // Task 1.5 Get user by user id
        User getID = HttpUtil.sendGetByUserID(URI.create((USERS)), 10);
        System.out.println(getID);

        // Task 1.6 Get user by username
        String username = "Moriah.Stanton";
        User getName = HttpUtil.sendGetByUsername(URI.create(USERS), username);
        System.out.println(getName);

    }

    public static User createUser() {
        User.Address.Geo geo = new User.Address.Geo("123456", "123456");
        User.Address address = new User.Address("Iskrivska", "12", "Kyiv", "03186", geo);
        User.Company company = new User.Company("CD Projekt Red", "We Are Rebbels", "ccTLD");

        return new User(11, "Orest", "Dymarchuk", "email@email.com", address, "123-456-789", "abracadabra.com", company);
    }
}
