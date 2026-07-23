package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Election {

    private String electionId;
    private String electionName;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public String getElectionName() {
        return electionName;
    }

    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String toFileString() {
        return  electionId + "|" +
                electionName + "|" +
                position + "|" +
                status;
    }


    public static Election fromFileString(String line) {
        String[] data = line.split("\\|");

        Election election = new Election();

        election.setElectionId(data[0]);
        election.setElectionName(data[1]);
        election.setPosition(data[2]);
        election.setStatus(data[3]);

        return election;
    }


    @Override
    public String toString() {
        return "Election ID : " + electionId +
                "\nElection Name : " + electionName +
                "\nPosition : " + position +
                "\nStatus : " + status;
    }

}
