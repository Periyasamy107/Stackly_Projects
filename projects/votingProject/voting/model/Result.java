package model;

public class Result {

    private String resultId;
    private String electionId;
    private String candidateId;
    private String candidateName;
    private int voteCount;
    private String position;
    private String resultStatus;

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String toFileString() {
        return resultId + "|" +
                electionId + "|" +
                candidateId + "|" +
                candidateName + "|" +
                voteCount + "|" +
                position + "|" +
                resultStatus;
    }


    public static Result fromFileString(String line) {
        String[] data = line.split("\\|");

        Result result =  new Result();

        result.setResultId(data[0]);
        result.setElectionId(data[1]);
        result.setCandidateId(data[2]);
        result.setCandidateName(data[3]);
        result.setVoteCount(Integer.parseInt(data[4]));
        result.setPosition(data[5]);
        result.setResultStatus(data[6]);

        return result;

    }


    @Override
    public String toString() {
        return "Result ID : " + resultId +
                "\nElection ID : " + electionId +
                "\nCandidate ID : " + candidateId +
                "\nCandidate Name : " + candidateName +
                "\nVote Count : " + voteCount +
                "\nPosition : " + position +
                "\nResult Status : " + resultStatus;
    }

}
