import java.util.Scanner;

public class JobPosting {

    private final JobService jobService;
    private final Scanner sc;

    public JobPosting(
            JobService jobService,
            Scanner sc) {

        this.jobService = jobService;
        this.sc = sc;
    }

    public void showMenu(
            String recruiterId,
            String companyName) {

        while (true) {

            System.out.println(
                    "\n===== JOB POSTING MANAGEMENT ====="
            );

            System.out.println("1. Post New Job");
            System.out.println("2. View My Jobs");
            System.out.println("3. Update Job");
            System.out.println("4. Delete Job");
            System.out.println("5. Back");

            Integer choice =
                    InputValidator.readChoice(
                            sc,
                            "Enter choice: ",
                            1,
                            5
                    );

            if (choice == null) {
                return;
            }

            if (choice == 5) {
                return;
            }

            switch (choice) {

                case 1:
                    postNewJob(
                            recruiterId,
                            companyName
                    );
                    break;

                case 2:
                    jobService.viewMyJobs(
                            recruiterId
                    );
                    break;

                case 3:
                    updateJob(
                            recruiterId,
                            companyName
                    );
                    break;

                case 4:
                    deleteJob(recruiterId);
                    break;
            }
        }
    }

    private void postNewJob(
            String recruiterId,
            String companyName) {

        System.out.println(
                "\n===== POST NEW JOB ====="
        );

        String title =
                InputValidator.readValidated(
                        sc,
                        "Job Title: ",
                        InputValidator::validText
                );

        if (title == null) {
            return;
        }

        String location =
                InputValidator.readValidated(
                        sc,
                        "Location: ",
                        InputValidator::validText
                );

        if (location == null) {
            return;
        }

        Double salary =
                readSalary();

        if (salary == null) {
            return;
        }

        String experience =
                InputValidator.readValidated(
                        sc,
                        "Experience (e.g. 0-2 Years): ",
                        InputValidator::validText
                );

        if (experience == null) {
            return;
        }

        String skills =
                InputValidator.readValidated(
                        sc,
                        "Skills Required: ",
                        InputValidator::validText
                );

        if (skills == null) {
            return;
        }

        String description =
                InputValidator.readValidated(
                        sc,
                        "Job Description: ",
                        InputValidator::validText
                );

        if (description == null) {
            return;
        }

        String company = companyName;

        if (company == null
                || company.trim().isEmpty()) {

            company =
                    InputValidator.readValidated(
                            sc,
                            "Company Name: ",
                            InputValidator::validText
                    );

            if (company == null) {
                return;
            }
        }

        Job job =
                jobService.postJob(
                        recruiterId,
                        title,
                        company,
                        location,
                        salary,
                        experience,
                        skills,
                        description
                );

        System.out.println(
                "\nJob Posted Successfully!"
        );

        System.out.println(
                "Your Job ID: "
                        + job.getJobId()
        );
    }

    private void updateJob(
            String recruiterId,
            String companyName) {

        String jobId =
                InputValidator.readValidated(
                        sc,
                        "Enter Job ID: ",
                        InputValidator::validId
                );

        if (jobId == null) {
            return;
        }

        Job existing =
                jobService.findJobById(jobId);

        if (existing == null
                || !existing.getRecruiterId()
                .equalsIgnoreCase(recruiterId)) {

            System.out.println(
                    "Job not found in your listings."
            );

            return;
        }

        System.out.println(
                "Enter the new details:"
        );

        String title =
                InputValidator.readValidated(
                        sc,
                        "Job Title: ",
                        InputValidator::validText
                );

        if (title == null) {
            return;
        }

        String company = companyName;

        if (company == null
                || company.trim().isEmpty()) {

            company =
                    InputValidator.readValidated(
                            sc,
                            "Company Name: ",
                            InputValidator::validText
                    );

            if (company == null) {
                return;
            }
        }

        String location =
                InputValidator.readValidated(
                        sc,
                        "Location: ",
                        InputValidator::validText
                );

        if (location == null) {
            return;
        }

        Double salary =
                readSalary();

        if (salary == null) {
            return;
        }

        String experience =
                InputValidator.readValidated(
                        sc,
                        "Experience: ",
                        InputValidator::validText
                );

        if (experience == null) {
            return;
        }

        String skills =
                InputValidator.readValidated(
                        sc,
                        "Skills Required: ",
                        InputValidator::validText
                );

        if (skills == null) {
            return;
        }

        String description =
                InputValidator.readValidated(
                        sc,
                        "Description: ",
                        InputValidator::validText
                );

        if (description == null) {
            return;
        }

        boolean updated =
                jobService.updateJob(
                        jobId,
                        recruiterId,
                        title,
                        company,
                        location,
                        salary,
                        experience,
                        skills,
                        description
                );

        System.out.println(
                updated
                        ? "Job updated successfully!"
                        : "Unable to update job."
        );
    }

    private void deleteJob(
            String recruiterId) {

        String jobId =
                InputValidator.readValidated(
                        sc,
                        "Enter Job ID: ",
                        InputValidator::validId
                );

        if (jobId == null) {
            return;
        }

        Job existing =
                jobService.findJobById(jobId);

        if (existing == null
                || !existing.getRecruiterId()
                .equalsIgnoreCase(recruiterId)) {

            System.out.println(
                    "Job not found in your listings."
            );

            return;
        }

        String confirm =
                InputValidator.readValidated(
                        sc,
                        "Are you sure? (yes/no): ",
                        value ->
                                value.equalsIgnoreCase("yes")
                                        || value.equalsIgnoreCase("no")
                );

        if (confirm == null) {
            return;
        }

        if (confirm.equalsIgnoreCase("yes")) {

            boolean deleted =
                    jobService.deleteJob(
                            jobId,
                            recruiterId
                    );

            System.out.println(
                    deleted
                            ? "Job deleted successfully!"
                            : "Unable to delete job."
            );

        } else {

            System.out.println(
                    "Delete cancelled."
            );
        }
    }

    private Double readSalary() {

        for (int attempt = 1;
             attempt <= 3;
             attempt++) {

            System.out.print(
                    "Salary (LPA): "
            );

            String value =
                    sc.nextLine().trim();

            try {

                double salary =
                        Double.parseDouble(value);

                if (salary > 0
                        && Double.isFinite(salary)) {

                    return salary;
                }

            } catch (NumberFormatException ignored) {
            }

            System.out.println(
                    "Invalid salary."
            );

            System.out.println(
                    "Salary must be a number greater than zero."
            );

            InputValidator.showRemaining(
                    attempt
            );
        }

        System.out.println(
                "Returning to previous menu..."
        );

        return null;
    }
}