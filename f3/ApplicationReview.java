import java.util.List;
import java.util.Scanner;

public class ApplicationReview {

    private final ApplicationManager manager;
    private final Scanner sc;

    public ApplicationReview(
            ApplicationManager manager,
            Scanner sc) {

        this.manager = manager;
        this.sc = sc;
    }

    public void showRecruiterReview(
            String recruiterId,
            JobService jobService) {

        while (true) {

            System.out.println(
                    "\n===== APPLICATION REVIEW ====="
            );

            System.out.println("1. View Applications");
            System.out.println("2. Review Application");
            System.out.println("3. Shortlist Candidate");
            System.out.println("4. Reject Candidate");
            System.out.println("5. Back");

            Integer choice =
                    InputValidator.readChoice(
                            sc,
                            "Enter choice: ",
                            1,
                            5
                    );

            if (choice == null
                    || choice == 5) {

                return;
            }

            switch (choice) {

                case 1:
                    viewApplications(
                            recruiterId,
                            jobService
                    );
                    break;

                case 2:
                    review(
                            recruiterId,
                            jobService
                    );
                    break;

                case 3:
                    update(
                            recruiterId,
                            jobService,
                            "Shortlisted"
                    );
                    break;

                case 4:
                    update(
                            recruiterId,
                            jobService,
                            "Rejected"
                    );
                    break;
            }
        }
    }

    private void viewApplications(
            String recruiterId,
            JobService jobService) {

        boolean found = false;

        for (Job job :
                jobService.getAllJobs()) {

            if (job.getRecruiterId()
                    .equalsIgnoreCase(
                            recruiterId
                    )) {

                List<Application> list =
                        manager.getApplicationsByJob(
                                job.getJobId()
                        );

                for (Application application :
                        list) {

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

    private void review(
            String recruiterId,
            JobService jobService) {

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
                manager.findApplication(id);

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
                        recruiterId
                )) {

            System.out.println(
                    "You can only review applications "
                            + "for your jobs."
            );

            return;
        }

        application.displayApplication();
    }

    private void update(
            String recruiterId,
            JobService jobService,
            String status) {

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
                manager.findApplication(id);

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
                        recruiterId
                )) {

            System.out.println(
                    "You can only update applications "
                            + "for your jobs."
            );

            return;
        }

        System.out.println(
                manager.updateApplicationStatus(
                        id,
                        status
                )
                        ? "Application status updated to "
                        + status + "."
                        : "Application not found."
        );
    }
}