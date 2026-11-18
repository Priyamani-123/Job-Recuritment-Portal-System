public class Recruiter
        extends User
        implements Login {

    private String companyName;
    private boolean loggedIn;

    public Recruiter(
            int userId,
            String name,
            String email,
            String password,
            String companyName) {

        super(
                userId,
                name,
                email,
                password,
                "RECRUITER"
        );

        this.companyName = companyName;
        this.loggedIn = false;
    }

    @Override
    public boolean login(
            String email,
            String password) {

        if (getEmail().equalsIgnoreCase(email)
                && getPassword().equals(password)) {

            loggedIn = true;
            return true;
        }

        return false;
    }

    public void logout() {
        loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void displayProfile() {

        System.out.println(
                "\n===== RECRUITER PROFILE ====="
        );

        System.out.println(
                "User ID       : "
                        + getUserId()
        );

        System.out.println(
                "Name          : "
                        + getName()
        );

        System.out.println(
                "Email         : "
                        + getEmail()
        );

        System.out.println(
                "Company       : "
                        + companyName
        );

        System.out.println(
                "Role          : "
                        + getRole()
        );
    }

    public void updateName(
            String name) {

        setName(name);
    }

    public void updateEmail(
            String email) {

        setEmail(email);
    }

    public void updatePassword(
            String password) {

        setPassword(password);
    }

    public void updateCompanyName(
            String companyName) {

        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}