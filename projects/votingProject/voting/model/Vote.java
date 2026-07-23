package model;

import java.time.LocalDateTime;

public class Vote {

    private String voteId;
    private String electionId;
    private String studentId;
    private String candidateId;
    private LocalDateTime voteDateTime;
    private String status;

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public LocalDateTime getVoteDateTime() {
        return voteDateTime;
    }

    public void setVoteDateTime(LocalDateTime voteDateTime) {
        this.voteDateTime = voteDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String toFileString() {
        return voteId + "|" +
                electionId + "|" +
                studentId + "|" +
                candidateId + "|" +
                voteDateTime + "|" +
                status;
    }

    public static Vote fromFileString(String line) {
        String [] data = line.split("\\|");

        Vote vote = new Vote();

        vote.setVoteId(data[0]);
        vote.setElectionId(data[1]);
        vote.setStudentId(data[2]);
        vote.setCandidateId(data[3]);
        vote.setVoteDateTime(LocalDateTime.parse(data[4]));
        vote.setStatus(data[5]);

        return vote;

    }


    @Override
    public String toString() {
        return "Vote ID : " + voteId +
                "\nElection ID : " + electionId +
                "\nStudent ID : " + studentId +
                "\nCandidate ID : " + candidateId +
                "\nVoting time : " + voteDateTime +
                "\nVoting Status : " + status;
    }


























}
