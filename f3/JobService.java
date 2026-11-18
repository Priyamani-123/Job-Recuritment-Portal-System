import java.util.ArrayList;
import java.util.List;

public class JobService {

    private final List<Job> jobs =
            new ArrayList<>();

    private int nextJobNumber = 1001;

    public Job postJob(
            String recruiterId,
            String title,
            String company,
            String location,
            double salary,
            String experience,
            String skills,
            String description) {

        String jobId =
                "J" + nextJobNumber++;

        Job job =
                new Job(
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

        jobs.add(job);

        return job;
    }

    public void viewMyJobs(
            String recruiterId) {

        boolean found = false;

        System.out.println(
                "\n===== MY JOBS ====="
        );

        for (Job job : jobs) {

            if (job.getRecruiterId()
                    .equalsIgnoreCase(recruiterId)) {

                job.displayJob();
                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "You have not posted any jobs."
            );
        }
    }

    public Job findJobById(
            String jobId) {

        for (Job job : jobs) {

            if (job.getJobId()
                    .equalsIgnoreCase(jobId)) {

                return job;
            }
        }

        return null;
    }

    public boolean updateJob(
            String jobId,
            String recruiterId,
            String title,
            String company,
            String location,
            double salary,
            String experience,
            String skills,
            String description) {

        Job job =
                findJobById(jobId);

        if (job == null
                || !job.getRecruiterId()
                .equalsIgnoreCase(recruiterId)) {

            return false;
        }

        job.setJobTitle(title);
        job.setCompany(company);
        job.setLocation(location);
        job.setSalaryLpa(salary);
        job.setExperience(experience);
        job.setSkillsRequired(skills);
        job.setDescription(description);

        return true;
    }

    public boolean deleteJob(
            String jobId,
            String recruiterId) {

        Job job =
                findJobById(jobId);

        if (job == null
                || !job.getRecruiterId()
                .equalsIgnoreCase(recruiterId)) {

            return false;
        }

        jobs.remove(job);

        return true;
    }

    public List<Job> getAllJobs() {

        return new ArrayList<>(jobs);
    }
}