public class Job {

    private String jobId;
    private String recruiterId;
    private String jobTitle;
    private String company;
    private String location;
    private double salaryLpa;
    private String experience;
    private String skillsRequired;
    private String description;

    public Job(
            String jobId,
            String recruiterId,
            String jobTitle,
            String company,
            String location,
            double salaryLpa,
            String experience,
            String skillsRequired,
            String description) {

        this.jobId = jobId;
        this.recruiterId = recruiterId;
        this.jobTitle = jobTitle;
        this.company = company;
        this.location = location;
        this.salaryLpa = salaryLpa;
        this.experience = experience;
        this.skillsRequired = skillsRequired;
        this.description = description;
    }

    public String getJobId() {
        return jobId;
    }

    public String getRecruiterId() {
        return recruiterId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public double getSalaryLpa() {
        return salaryLpa;
    }

    public String getExperience() {
        return experience;
    }

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public String getDescription() {
        return description;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSalaryLpa(double salaryLpa) {
        this.salaryLpa = salaryLpa;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void displayJob() {

        System.out.println(
                "----------------------------------"
        );

        System.out.println(
                "Job ID          : " + jobId
        );

        System.out.println(
                "Recruiter ID    : " + recruiterId
        );

        System.out.println(
                "Job Title       : " + jobTitle
        );

        System.out.println(
                "Company         : " + company
        );

        System.out.println(
                "Location        : " + location
        );

        System.out.println(
                "Salary          : " + salaryLpa + " LPA"
        );

        System.out.println(
                "Experience      : " + experience
        );

        System.out.println(
                "Skills Required : " + skillsRequired
        );

        System.out.println(
                "Description     : " + description
        );

        System.out.println(
                "----------------------------------"
        );
    }
}