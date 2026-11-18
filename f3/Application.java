public class Application {

    private String applicationId;
    private String candidateId;
    private String candidateName;
    private String jobId;
    private String jobTitle;
    private String status;

    public Application(
            String applicationId,
            String candidateId,
            String candidateName,
            String jobId,
            String jobTitle) {

        this.applicationId = applicationId;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.status = "Applied";
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getJobId() {
        return jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {

        this.status = status;
    }

    public void displayApplication() {

        System.out.println(
                "----------------------------------"
        );

        System.out.println(
                "Application ID : "
                        + applicationId
        );

        System.out.println(
                "Candidate ID   : "
                        + candidateId
        );

        System.out.println(
                "Candidate Name : "
                        + candidateName
        );

        System.out.println(
                "Job ID         : "
                        + jobId
        );

        System.out.println(
                "Job Title      : "
                        + jobTitle
        );

        System.out.println(
                "Status         : "
                        + status
        );

        System.out.println(
                "----------------------------------"
        );
    }
}