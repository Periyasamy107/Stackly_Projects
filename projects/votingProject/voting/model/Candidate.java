package model;

import java.time.LocalDate;

public class Candidate {

    private String candidateId;
    private String studentId;
    private String candidateName;
    private String electionId;
    private String department;
    private String position;
    private String manifesto;
    private int voteCount;
    private LocalDate nominationDate;

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getManifesto() {
        return manifesto;
    }

    public void setManifesto(String manifesto) {
        this.manifesto = manifesto;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public LocalDate getNominationDate() {
        return nominationDate;
    }

    public void setNominationDate(LocalDate nominationDate) {
        this.nominationDate = nominationDate;
    }


    public String toFileString() {
        return candidateId + "|" +
                studentId + "|" +
                candidateName + "|" +
                electionId + "|" +
                department + "|" +
                position + "|" +
                manifesto + "|" +
                voteCount + "|" +
                nominationDate;
    }


    public static Candidate fromFileString(String line) {
        String[] data = line.split("\\|");
        Candidate candidate = new Candidate();

        candidate.setCandidateId(data[0]);
        candidate.setStudentId(data[1]);
        candidate.setCandidateName(data[2]);
        candidate.setElectionId(data[3]);
        candidate.setDepartment(data[4]);
        candidate.setPosition(data[5]);
        candidate.setManifesto(data[6]);
        candidate.setVoteCount(Integer.parseInt(data[7]));
        candidate.setNominationDate(LocalDate.parse(data[8]));

        return candidate;
    }


    public String toString() {
        return "Candidate ID : " + candidateId +
                "\nStudent ID : " + studentId +
                "\nCandidate Name : " + candidateName +
                "\nElection ID : " + electionId +
                "\nDepartment : " + department +
                "\nPosition : " + position +
                "\nManifesto : " + manifesto +
                "\nVote Count : " + voteCount +
                "\nNomination Date : " + nominationDate;
    }

}
