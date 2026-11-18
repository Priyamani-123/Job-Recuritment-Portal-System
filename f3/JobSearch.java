import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JobSearch {

    private final JobService jobService;
    private final ApplicationManager applicationManager;
    private final Scanner sc;

    public JobSearch(
            JobService jobService,
            ApplicationManager applicationManager,
            Scanner sc) {

        this.jobService = jobService;
        this.applicationManager = applicationManager;
        this.sc = sc;
    }

    public void showCandidateMenu(
            String candidateId,
            String candidateName) {

        while (true) {

            System.out.println(
                    "\n===== JOB SEARCH / APPLICATION ====="
            );

            System.out.println("1. View All Jobs");
            System.out.println("2. Search by Title");
            System.out.println("3. Search by Location");
            System.out.println("4. View Job Details");
            System.out.println("5. Apply for Job");
            System.out.println("6. Back");

            Integer choice =
                    InputValidator.readChoice(
                            sc,
                            "Enter choice: ",
                            1,
                            6
                    );

            if (choice == null) {
                return;
            }

            if (choice == 6) {
                return;
            }

            switch (choice) {

                case 1:
                    display(
                            jobService.getAllJobs()
                    );
                    break;

                case 2:
                    searchTitle();
                    break;

                case 3:
                    searchLocation();
                    break;

                case 4:
                    details();
                    break;

                case 5:
                    apply(
                            candidateId,
                            candidateName
                    );
                    break;
            }
        }
    }

    private void display(
            List<Job> jobs) {

        if (jobs.isEmpty()) {

            System.out.println(
                    "No jobs found."
            );

            return;
        }

        for (Job job : jobs) {
            job.displayJob();
        }
    }

    private void searchTitle() {

        String query =
                InputValidator.readValidated(
                        sc,
                        "Enter title: ",
                        InputValidator::validText
                );

        if (query == null) {
            return;
        }

        List<Job> result =
                new ArrayList<>();

        for (Job job :
                jobService.getAllJobs()) {

            if (job.getJobTitle()
                    .toLowerCase()
                    .contains(
                            query.toLowerCase()
                    )) {

                result.add(job);
            }
        }

        display(result);
    }

    private void searchLocation() {

        String query =
                InputValidator.readValidated(
                        sc,
                        "Enter location: ",
                        InputValidator::validText
                );

        if (query == null) {
            return;
        }

        List<Job> result =
                new ArrayList<>();

        for (Job job :
                jobService.getAllJobs()) {

            if (job.getLocation()
                    .toLowerCase()
                    .contains(
                            query.toLowerCase()
                    )) {

                result.add(job);
            }
        }

        display(result);
    }

    private void details() {

        String id =
                InputValidator.readValidated(
                        sc,
                        "Enter Job ID: ",
                        InputValidator::validId
                );

        if (id == null) {
            return;
        }

        Job job =
                jobService.findJobById(id);

        if (job == null) {

            System.out.println(
                    "Job not found."
            );

        } else {

            job.displayJob();
        }
    }

    private void apply(
            String candidateId,
            String candidateName) {

        String id =
                InputValidator.readValidated(
                        sc,
                        "Enter Job ID: ",
                        InputValidator::validId
                );

        if (id == null) {
            return;
        }

        Job job =
                jobService.findJobById(id);

        if (job == null) {

            System.out.println(
                    "Job not found."
            );

            return;
        }

        Application application =
                applicationManager.apply(
                        candidateId,
                        candidateName,
                        job
                );

        if (application == null) {

            System.out.println(
                    "You have already applied for this job."
            );

        } else {

            System.out.println(
                    "Application submitted!"
            );

            System.out.println(
                    "Application ID: "
                            + application.getApplicationId()
            );
        }
    }
}