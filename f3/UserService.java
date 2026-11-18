import java.util.ArrayList;

public class UserService
        implements Login {

    private final ArrayList<User> users =
            new ArrayList<>();

    public boolean register(
            User user) {

        if (user == null) {
            return false;
        }

        if (emailExists(
                user.getEmail())) {

            return false;
        }

        for (User existing : users) {

            if (existing.getUserId()
                    == user.getUserId()) {

                return false;
            }
        }

        users.add(user);

        return true;
    }

    @Override
    public boolean login(
            String email,
            String password) {

        return getUserByEmailAndPassword(
                email,
                password
        ) != null;
    }

    public User getUserByEmail(
            String email) {

        if (email == null) {
            return null;
        }

        for (User user : users) {

            if (user.getEmail()
                    .equalsIgnoreCase(email)) {

                return user;
            }
        }

        return null;
    }

    public User getUserByEmailAndPassword(
            String email,
            String password) {

        if (email == null
                || password == null) {

            return null;
        }

        for (User user : users) {

            if (user.getEmail()
                    .equalsIgnoreCase(email)
                    && user.getPassword()
                    .equals(password)) {

                return user;
            }
        }

        return null;
    }

    public boolean emailExists(
            String email) {

        return getUserByEmail(email)
                != null;
    }
}