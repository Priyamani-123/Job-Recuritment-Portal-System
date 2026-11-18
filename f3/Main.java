import java.util.*;


public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final JobService jobService = new JobService();
    private static final ApplicationManager applicationManager =
            new ApplicationManager();

    private static final JobPosting jobPosting =
            new JobPosting(jobService, sc);

    private static final JobSearch jobSearch =
            new JobSearch(jobService, applicationManager, sc);

    private static final ApplicationReview applicationReview =
            new ApplicationReview(applicationManager, sc);

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            System.out.println("\n====================================");
            System.out.println("        JOB RECRUITMENT SYSTEM   ");
            System.out.println("====================================");
            System.out.println("1. Registration");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            Integer choice =
                    InputValidator.readChoice(
                            sc,
                            "Enter your choice: ",
                            1,
                            3
                    );

            if (choice == null) {
                continue;
            }

            switch (choice) {

                case 1:
                    registration();
                    break;

                case 2:
                    login();
                    break;

                case 3:
                    System.out.println(
                            "Thank you for using the system."
                    );
                    running = false;
                    break;
            }
        }

        sc.close();
    }

    private static void registration() {

        System.out.println("\n===== REGISTRATION =====");
        System.out.println("1. Candidate");
        System.out.println("2. Recruiter");
        System.out.println("3. Back");

        Integer role =
                InputValidator.readChoice(
                        sc,
                        "Enter role: ",
                        1,
                        3
                );

        if (role == null || role == 3) {
            return;
        }

        Integer id =
                InputValidator.readInt(
                        sc,
                        "Enter User ID: ",
                        1,
                        Integer.MAX_VALUE
                );

        if (id == null) {
            return;
        }

        String name =
                InputValidator.readValidated(
                        sc,
                        "Enter Name: ",
                        InputValidator::validName
                );

        if (name == null) {
            return;
        }

        String email =
                InputValidator.readValidated(
                        sc,
                        "Enter Email: ",
                        InputValidator::validEmail
                );

        if (email == null) {
            return;
        }

        if (userService.emailExists(email)) {
            System.out.println("Email already registered.");
            System.out.println(
                    "Returning to main menu..."
            );
            return;
        }

        String password =
                InputValidator.readValidated(
                        sc,
                        "Enter Password: ",
                        InputValidator::validPassword
                );

        if (password == null) {
            return;
        }

        if (role == 1) {

            String username =
                    InputValidator.readValidated(
                            sc,
                            "Enter Username: ",
                            InputValidator::validUsername
                    );

            if (username == null) {
                return;
            }

            String phoneText =
                    InputValidator.readValidated(
                            sc,
                            "Enter Phone Number: ",
                            InputValidator::validPhone
                    );

            if (phoneText == null) {
                return;
            }

            long phone =
                    Long.parseLong(phoneText);

            String qualification =
                    InputValidator.readValidated(
                            sc,
                            "Enter Qualification: ",
                            InputValidator::validText
                    );

            if (qualification == null) {
                return;
            }

            String skills =
                    InputValidator.readValidated(
                            sc,
                            "Enter Skills: ",
                            InputValidator::validText
                    );

            if (skills == null) {
                return;
            }

            Integer experience =
                    InputValidator.readInt(
                            sc,
                            "Enter Experience (years): ",
                            0,
                            60
                    );

            if (experience == null) {
                return;
            }

            JobSeeker candidate =
                    new JobSeeker(
                            id,
                            name,
                            username,
                            email,
                            password,
                            phone,
                            qualification,
                            skills,
                            experience
                    );

            if (userService.register(candidate)) {

                System.out.println(
                        "\nCandidate registration successful."
                );

            } else {

                System.out.println(
                        "Registration failed. "
                        + "User ID or email may already exist."
                );
            }

        } else {

            String company =
                    InputValidator.readValidated(
                            sc,
                            "Enter Company Name: ",
                            InputValidator::validText
                    );

            if (company == null) {
                return;
            }

            Recruiter recruiter =
                    new Recruiter(
                            id,
                            name,
                            email,
                            password,
                            company
                    );

            if (userService.register(recruiter)) {

                System.out.println(
                        "\nRecruiter registration successful."
                );

            } else {

                System.out.println(
                        "Registration failed. "
                        + "User ID or email may already exist."
                );
            }
        }
    }

    private static void login() {

        System.out.println("\n===== LOGIN =====");

        for (int attempt = 1;
             attempt <= 3;
             attempt++) {

            String email =
                    InputValidator.readValidated(
                            sc,
                            "Enter Email: ",
                            InputValidator::validEmail
                    );

            if (email == null) {
                return;
            }

            String password =
                    InputValidator.readValidated(
                            sc,
                            "Enter Password: ",
                            InputValidator::validLoginPassword
                    );

            if (password == null) {
                return;
            }

            User user =
                    userService.getUserByEmailAndPassword(
                            email,
                            password
                    );

            if (user != null) {

                System.out.println(
                        "\nLogin successful! Welcome, "
                                + user.getName()
                );

                if (user instanceof Recruiter) {

                    recruiterDashboard(
                            (Recruiter) user
                    );

                } else if (user instanceof JobSeeker) {

                    candidateDashboard(
                            (JobSeeker) user
                    );
                }

                return;
            }

            System.out.println(
                    "Invalid email or password."
            );

            InputValidator.showRemaining(attempt);
        }

        System.out.println(
                "Returning to main menu..."
        );
    }

    private static void candidateDashboard(
            JobSeeker candidate) {

        while (true) {

            System.out.println(
                    "\n===== CANDIDATE DASHBOARD ====="
            );

            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Search Jobs / Apply");
            System.out.println("4. View Application Details");
            System.out.println("5. My Applications");
            System.out.println("6. Application Status");
            System.out.println("7. View All Jobs");
            System.out.println("8. Logout");

            Integer choice =
                    InputValidator.readChoice(
                            sc,
                            "Enter choice: ",
                            1,
                            8
                    );

            if (choice == null) {
                continue;
            }

            switch (choice) {

                case 1:
                    displayCandidate(candidate);
                    break;

                case 2:
                    updateCandidate(candidate);
                    break;

                case 3:
                    jobSearch.showCandidateMenu(
                            String.valueOf(
                                    candidate.getCandidateId()
                            ),
                            candidate.getName()
                    );
                    break;

                case 4:
                    viewApplicationDetails(
                            String.valueOf(
                                    candidate.getCandidateId()
                            )
                    );
                    break;

                case 5:
                    viewMyApplications(
                            String.valueOf(
                                    candidate.getCandidateId()
                            )
                    );
                    break;

                case 6:
                    viewApplicationStatus(
                            String.valueOf(
                                    candidate.getCandidateId()
                            )
                    );
                    break;

                case 7:

                    List<Job> jobs =
                            jobService.getAllJobs();

                    if (jobs.isEmpty()) {

                        System.out.println(
                                "No jobs available."
                        );

                    } else {

                        for (Job job : jobs) {
                            job.displayJob();
                        }
                    }

                    break;

                case 8:

                    System.out.println(
                            "Logging out..."
                    );

                    return;
            }
        }
    }

    private static void displayCandidate(
            JobSeeker candidate) {

        System.out.println(
                "\n===== CANDIDATE PROFILE ====="
        );

        System.out.println(
                "Candidate ID  : "
                        + candidate.getCandidateId()
        );

        System.out.println(
                "Name          : "
                        + candidate.getName()
        );

        System.out.println(
                "Username      : "
                        + candidate.getUserName()
        );

        System.out.println(
                "Email         : "
                        + candidate.getEmail()
        );

        System.out.println(
                "Phone         : "
                        + candidate.getPhoneNo()
        );

        System.out.println(
                "Qualification : "
                        + candidate.getQualification()
        );

        System.out.println(
                "Skills        : "
                        + candidate.getSkills()
        );

        System.out.println(
                "Experience    : "
                        + candidate.getExperience()
                        + " years"
        );
    }

    private static void updateCandidate(
            JobSeeker candidate) {

        while (true) {

            System.out.println(
                    "\n===== UPDATE PROFILE ====="
            );

            System.out.println("1. Name");
            System.out.println("2. Username");
            System.out.println("3. Email");
            System.out.println("4. Phone");
            System.out.println("5. Qualification");
            System.out.println("6. Skills");
            System.out.println("7. Experience");
            System.out.println("8. Password");
            System.out.println("9. Back");

            Integer choice =
                    InputValidator.readChoice(
                            sc,
                            "Enter choice: ",
                            1,
                            9
                    );

            if (choice == null) {
                continue;
            }

            if (choice == 9) {
                return;
            }

            switch (choice) {

                case 1:

                    String name =
                            InputValidator.readValidated(
                                    sc,
                                    "New Name: ",
                                    InputValidator::validName
                            );

                    if (name != null) {
                        candidate.setName(name);
                    }

                    break;

                case 2:

                    String username =
                            InputValidator.readValidated(
                                    sc,
                                    "New Username: ",
                                    InputValidator::validUsername
                            );

                    if (username != null) {
                        candidate.setUserName(username);
                    }

                    break;

                case 3:

                    String email =
                            InputValidator.readValidated(
                                    sc,
                                    "New Email: ",
                                    InputValidator::validEmail
                            );

                    if (email != null) {

                        User existing =
                                userService.getUserByEmail(email);

                        if (existing != null
                                && existing != candidate) {

                            System.out.println(
                                    "Email already registered."
                            );

                        } else {

                            candidate.setEmail(email);
                        }
                    }

                    break;

                case 4:

                    String phone =
                            InputValidator.readValidated(
                                    sc,
                                    "New Phone: ",
                                    InputValidator::validPhone
                            );

                    if (phone != null) {

                        candidate.setPhoneNo(
                                Long.parseLong(phone)
                        );
                    }

                    break;

                case 5:

                    String qualification =
                            InputValidator.readValidated(
                                    sc,
                                    "New Qualification: ",
                                    InputValidator::validText
                            );

                    if (qualification != null) {

                        candidate.setQualification(
                                qualification
                        );
                    }

                    break;

                case 6:

                    String skills =
                            InputValidator.readValidated(
                                    sc,
                                    "New Skills: ",
                                    InputValidator::validText
                            );

                    if (skills != null) {
                        candidate.setSkills(skills);
                    }

                    break;

                case 7:

                    Integer experience =
                            InputValidator.readInt(
                                    sc,
                                    "New Experience: ",
                                    0,
                                    60
                            );

                    if (experience != null) {
                        candidate.setExperience(
                                experience
                        );
                    }

                    break;

                case 8:

                    String password =
                            InputValidator.readValidated(
                                    sc,
                                    "New Password: ",
                                    InputValidator::validPassword
                            );

                    if (password != null) {
                        candidate.setPassword(password);
                    }

                    break;
            }

            System.out.println(
                    "Profile update operation completed."
            );
        }
    }

    private static void viewApplicationDetails(
            String candidateId) {

        String id =
                InputValidator.readValidated(
                        sc,
                        "Enter Application ID: ",
                        InputValidator::validId
                );

        if (id == null) {
            return;
        }

        Application application =
                applicationManager.findApplication(id);

        if (application == null
                || !application
                .getCandidateId()
                .equalsIgnoreCase(candidateId)) {

            System.out.println(
                    "Application not found."
            );

        } else {

            application.displayApplication();
        }
    }

    private static void viewMyApplications(
            String candidateId) {

        List<Application> applications =
                applicationManager
                        .getApplicationsByCandidate(
                                candidateId
                        );

        if (applications.isEmpty()) {

            System.out.println(
                    "No applications found."
            );

        } else {

            for (Application application :
                    applications) {

                application.displayApplication();
            }
        }
    }

    private static void viewApplicationStatus(
            String candidateId) {

        List<Application> applications =
                applicationManager
                        .getApplicationsByCandidate(
                                candidateId
                        );

        if (applications.isEmpty()) {

            System.out.println(
                    "No applications found."
            );

            return;
        }

        System.out.println(
                "\n===== APPLICATION STATUS ====="
        );

        for (Application application :
                applications) {

            System.out.println(
                    "Application ID: "
                            + application.getApplicationId()
                            + " | Job: "
                            + application.getJobTitle()
                            + " | Status: "
                            + application.getStatus()
            );
        }
    }

    private static void recruiterDashboard(
            Recruiter recruiter) {

        recruiter.login(
                recruiter.getEmail(),
                recruiter.getPassword()
        );

        while (true) {

            System.out.println(
                    "\n===== RECRUITER DASHBOARD ====="
            );

            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Post Jobs / Manage Jobs");
            System.out.println("4. View Posted Jobs");
            System.out.println("5. Application Review");
            System.out.println("6. Update Application Status");
            System.out.println("7. View All Applications");
            System.out.println("8. Logout");

            Integer choice =
                    InputValidator.readChoice(
                            sc,
                            "Enter choice: ",
                            1,
                            8
                    );

            if (choice == null) {
                continue;
            }

            switch (choice) {

                case 1:
                    recruiter.displayProfile();
                    break;

                case 2:
                    updateRecruiter(recruiter);
                    break;

                case 3:
                    jobPosting.showMenu(
                            String.valueOf(
                                    recruiter.getUserId()
                            ),
                            recruiter.getCompanyName()
                    );
                    break;

                case 4:
                    jobService.viewMyJobs(
                            String.valueOf(
                                    recruiter.getUserId()
                            )
                    );
                    break;

                case 5:
                    applicationReview
                            .showRecruiterReview(
                                    String.valueOf(
                                            recruiter.getUserId()
                                    ),
                                    jobService
                            );
                    break;

                case 6:
                    updateApplicationStatus(
                            recruiter
                    );
                    break;

                case 7:
                    viewAllApplicationsForRecruiter(
                            recruiter
                    );
                    break;

                case 8:

                    recruiter.logout();

                    System.out.println(
                            "Logged out successfully."
                    );

                    return;
            }
        }
    }

    private static void updateRecruiter(
            Recruiter recruiter) {

        while (true) {

            System.out.println(
                    "\n===== UPDATE RECRUITER PROFILE ====="
            );

            System.out.println("1. Name");
            System.out.println("2. Email");
            System.out.println("3. Password");
            System.out.println("4. Company");
            System.out.println("5. Back");

            Integer choice =
                    InputValidator.readChoice(
                            sc,
                            "Enter choice: ",
                            1,
                            5
                    );

            if (choice == null) {
                continue;
            }

            if (choice == 5) {
                return;
            }

            switch (choice) {

                case 1:

                    String name =
                            InputValidator.readValidated(
                                    sc,
                                    "New Name: ",
                                    InputValidator::validName
                            );

                    if (name != null) {
                        recruiter.updateName(name);
                    }

                    break;

                case 2:

                    String email =
                            InputValidator.readValidated(
                                    sc,
                                    "New Email: ",
                                    InputValidator::validEmail
                            );

                    if (email != null) {

                        User existing =
                                userService.getUserByEmail(email);

                        if (existing != null
                                && existing != recruiter) {

                            System.out.println(
                                    "Email already registered."
                            );

                        } else {

                            recruiter.updateEmail(email);
                        }
                    }

                    break;

                case 3:

                    String password =
                            InputValidator.readValidated(
                                    sc,
                                    "New Password: ",
                                    InputValidator::validPassword
                            );

                    if (password != null) {
                        recruiter.updatePassword(
                                password
                        );
                    }

                    break;

                case 4:

                    String company =
                            InputValidator.readValidated(
                                    sc,
                                    "New Company Name: ",
                                    InputValidator::validText
                            );

                    if (company != null) {
                        recruiter.updateCompanyName(
                                company
                        );
                    }

                    break;
            }

            System.out.println(
                    "Profile update operation completed."
            );
        }
    }

    private static void updateApplicationStatus(
            Recruiter recruiter) {

        String id =
                InputValidator.readValidated(
                        sc,
                        "Application ID: ",
                        InputValidator::validId
                );

        if (id == null) {
            return;
        }

        Application application =
                applicationManager.findApplication(id);

        if (application == null) {

            System.out.println(
                    "Application not found."
            );

            return;
        }

        Job job =
                jobService.findJobById(
                        application.getJobId()
                );

        if (job == null
                || !job.getRecruiterId()
                .equalsIgnoreCase(
                        String.valueOf(
                                recruiter.getUserId()
                        )
                )) {

            System.out.println(
                    "You can only update applications "
                            + "for your jobs."
            );

            return;
        }

        System.out.println("1. Shortlisted");
        System.out.println("2. Rejected");
        System.out.println("3. Selected");
        System.out.println("4. Under Review");
        System.out.println("5. Back");

        Integer choice =
                InputValidator.readChoice(
                        sc,
                        "Choose status: ",
                        1,
                        5
                );

        if (choice == null || choice == 5) {
            return;
        }

        String status;

        switch (choice) {

            case 1:
                status = "Shortlisted";
                break;

            case 2:
                status = "Rejected";
                break;

            case 3:
                status = "Selected";
                break;

            default:
                status = "Under Review";
        }

        System.out.println(
                applicationManager
                        .updateApplicationStatus(
                                id,
                                status
                        )
                        ? "Status updated successfully."
                        : "Unable to update status."
        );
    }

    private static void viewAllApplicationsForRecruiter(
            Recruiter recruiter) {

        boolean found = false;

        for (Job job :
                jobService.getAllJobs()) {

            if (job.getRecruiterId()
                    .equalsIgnoreCase(
                            String.valueOf(
                                    recruiter.getUserId()
                            )
                    )) {

                for (Application application :
                        applicationManager
                                .getApplicationsByJob(
                                        job.getJobId()
                                )) {

                    application.displayApplication();
                    found = true;
                }
            }
        }

        if (!found) {

            System.out.println(
                    "No applications found for your jobs."
            );
        }
    }
}


/*
 * INPUT VALIDATOR
 *
 * There is only ONE Main class and ONE main() method.
 */
class InputValidator {

    private static final int MAX_ATTEMPTS = 3;

    interface Validator {
        boolean test(String value);
    }

    static String readValidated(
            Scanner sc,
            String prompt,
            Validator validator) {

        for (int attempt = 1;
             attempt <= MAX_ATTEMPTS;
             attempt++) {

            System.out.print(prompt);

            String value =
                    sc.nextLine().trim();

            if (validator.test(value)) {
                return value;
            }

            printSpecificError(prompt);

            showRemaining(attempt);
        }

        System.out.println(
                "Returning to previous menu..."
        );

        return null;
    }

    private static void printSpecificError(
            String prompt) {

        String p =
                prompt.toLowerCase();

        if (p.contains("email")) {

            System.out.println(
                    "Invalid email."
            );

            System.out.println(
                    "Email must be in the format: example@gmail.com"
            );

            System.out.println(
                    "Example: shiva@gmail.com"
            );

        } else if (p.contains("password")) {

            System.out.println(
                    "Invalid password."
            );

            System.out.println(
                    "Password must contain at least 8 characters, "
                            + "one uppercase letter, "
                            + "one lowercase letter, "
                            + "one number, "
                            + "and one special character."
            );

            System.out.println(
                    "Example: Shiva@123"
            );

        } else if (p.contains("phone")) {

            System.out.println(
                    "Invalid phone number."
            );

            System.out.println(
                    "Phone number must contain exactly 10 digits "
                            + "and must not start with 0."
            );

        } else if (p.contains("username")) {

            System.out.println(
                    "Invalid username."
            );

            System.out.println(
                    "Username must start with a letter "
                            + "and contain 3-20 letters, "
                            + "numbers, or underscore."
            );

        } else if (p.contains("name")) {

            System.out.println(
                    "Invalid name."
            );

            System.out.println(
                    "Name must contain letters and may contain "
                            + "spaces, apostrophes, or hyphens."
            );

        } else if (p.contains("job id")
                || p.contains("application id")) {

            System.out.println(
                    "Invalid ID."
            );

            System.out.println(
                    "ID must start with a letter and contain "
                            + "only letters and numbers."
            );

        } else if (p.contains("choice")
                || p.contains("role")
                || p.contains("status")) {

            System.out.println(
                    "Invalid choice."
            );

            System.out.println(
                    "Please enter one of the numbers shown "
                            + "in the menu."
            );

        } else {

            System.out.println(
                    "Invalid input."
            );

            System.out.println(
                    "This field cannot be empty and must not "
                            + "exceed 200 characters."
            );
        }
    }

    static Integer readInt(
            Scanner sc,
            String prompt,
            int min,
            int max) {

        for (int attempt = 1;
             attempt <= MAX_ATTEMPTS;
             attempt++) {

            System.out.print(prompt);

            String value =
                    sc.nextLine().trim();

            try {

                int number =
                        Integer.parseInt(value);

                if (number >= min
                        && number <= max) {

                    return number;
                }

                System.out.println(
                        "Invalid number. Enter a value "
                                + "between "
                                + min
                                + " and "
                                + max
                                + "."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid number. Please enter "
                                + "digits only."
                );
            }

            showRemaining(attempt);
        }

        System.out.println(
                "Returning to previous menu..."
        );

        return null;
    }

    static Integer readChoice(
            Scanner sc,
            String prompt,
            int min,
            int max) {

        return readInt(
                sc,
                prompt,
                min,
                max
        );
    }

    static void showRemaining(
            int attempt) {

        int remaining =
                MAX_ATTEMPTS - attempt;

        if (remaining == 2) {

            System.out.println(
                    "You have 2 attempts more."
            );

        } else if (remaining == 1) {

            System.out.println(
                    "You have 1 attempt more."
            );

        } else {

            System.out.println(
                    "You have reached the maximum of 3 attempts."
            );
        }
    }

    static boolean validName(
            String value) {

        return value != null
                && value.matches(
                "[A-Za-z][A-Za-z .'-]{1,49}"
        );
    }

    static boolean validUsername(
            String value) {

        return value != null
                && value.matches(
                "[A-Za-z][A-Za-z0-9_]{2,19}"
        );
    }

    /*
     * Gmail validation.
     *
     * Valid:
     * abc@gmail.com
     * shiva@gmail.com
     *
     * Invalid:
     * abc@gmail
     * abc@g.mail
     * abc@yahoo.com
     * abc.com
     */
    static boolean validEmail(
            String value) {

        return value != null
                && value.matches(
                "^[A-Za-z0-9._%+-]+@gmail\\.com$"
        );
    }

    /*
     * Password requirements:
     *
     * At least 8 characters
     * One uppercase letter
     * One lowercase letter
     * One number
     * One special character
     * No spaces
     */
    static boolean validPassword(
            String value) {

        return value != null
                && value.matches(
                "^(?=.*[A-Z])"
                        + "(?=.*[a-z])"
                        + "(?=.*[0-9])"
                        + "(?=.*[^A-Za-z0-9])"
                        + "\\S{8,50}$"
        );
    }

    static boolean validLoginPassword(
            String value) {

        return value != null
                && !value.isEmpty()
                && !value.contains(" ");
    }

    static boolean validPhone(
            String value) {

        return value != null
                && value.matches(
                "[1-9][0-9]{9}"
        );
    }

    static boolean validText(
            String value) {

        return value != null
                && !value.trim().isEmpty()
                && value.trim().length() <= 200;
    }

    static boolean validId(
            String value) {

        return value != null
                && value.matches(
                "[A-Za-z][A-Za-z0-9]{1,19}"
        );
    }
}