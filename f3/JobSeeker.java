public class JobSeeker extends User {

    private int candidateId;
    private String userName;
    private long phoneNo;
    private String qualification;
    private String skills;
    private int experience;

    public JobSeeker(
            int candidateId,
            String name,
            String userName,
            String email,
            String password,
            long phoneNo,
            String qualification,
            String skills,
            int experience) {

        super(
                candidateId,
                name,
                email,
                password,
                "CANDIDATE"
        );

        this.candidateId = candidateId;
        this.userName = userName;
        this.phoneNo = phoneNo;
        this.qualification = qualification;
        this.skills = skills;
        this.experience = experience;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public String getUserName() {
        return userName;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public String getQualification() {
        return qualification;
    }

    public String getSkills() {
        return skills;
    }

    public int getExperience() {
        return experience;
    }

    public void setCandidateId(
            int candidateId) {

        this.candidateId = candidateId;
    }

    public void setUserName(
            String userName) {

        this.userName = userName;
    }

    public void setPhoneNo(
            long phoneNo) {

        this.phoneNo = phoneNo;
    }

    public void setQualification(
            String qualification) {

        this.qualification = qualification;
    }

    public void setSkills(
            String skills) {

        this.skills = skills;
    }

    public void setExperience(
            int experience) {

        this.experience = experience;
    }
}