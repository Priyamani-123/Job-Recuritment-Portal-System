import java.util.ArrayList;
import java.util.List;

public class ApplicationManager {

    private final List<Application> applications =
            new ArrayList<>();

    private int nextApplicationNumber = 5001;

    public Application apply(
            String candidateId,
            String candidateName,
            Job job) {

        for (Application application :
                applications) {

            if (application
                    .getCandidateId()
                    .equalsIgnoreCase(candidateId)
                    && application
                    .getJobId()
                    .equalsIgnoreCase(
                            job.getJobId()
                    )) {

                return null;
            }
        }

        String applicationId =
                "A" + nextApplicationNumber++;

        Application application =
                new Application(
                        applicationId,
                        candidateId,
                        candidateName,
                        job.getJobId(),
                        job.getJobTitle()
                );

        applications.add(application);

        return application;
    }

    public List<Application>
    getApplicationsByCandidate(
            String candidateId) {

        List<Application> result =
                new ArrayList<>();

        for (Application application :
                applications) {

            if (application
                    .getCandidateId()
                    .equalsIgnoreCase(candidateId)) {

                result.add(application);
            }
        }

        return result;
    }

    public List<Application>
    getApplicationsByJob(
            String jobId) {

        List<Application> result =
                new ArrayList<>();

        for (Application application :
                applications) {

            if (application
                    .getJobId()
                    .equalsIgnoreCase(jobId)) {

                result.add(application);
            }
        }

        return result;
    }

    public Application findApplication(
            String applicationId) {

        for (Application application :
                applications) {

            if (application
                    .getApplicationId()
                    .equalsIgnoreCase(
                            applicationId
                    )) {

                return application;
            }
        }

        return null;
    }

    public boolean updateApplicationStatus(
            String applicationId,
            String status) {

        Application application =
                findApplication(
                        applicationId
                );

        if (application == null) {
            return false;
        }

        application.setStatus(status);

        return true;
    }

    public List<Application>
    getAllApplications() {

        return new ArrayList<>(
                applications
        );
    }
}