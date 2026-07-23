package service;

import enums.AuditAction;
import enums.AuditModule;
import enums.ResultStatus;
import exception.UserNotFoundException;
import file.ResultFileManager;
import interfaces.*;
import model.Candidate;
import model.Result;
import model.Vote;
import repository.ResultRepository;
import repositoryImpl.ResultRepositoryImpl;
import util.IDGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultServiceImpl implements ResultService {

    private ResultRepository repository;
    private ResultFileManager fileManager;
    private CandidateService candidateService;
    private VotingService votingService;
    private AuditService auditService;

    public ResultServiceImpl() {
        repository = new ResultRepositoryImpl();
        fileManager = new ResultFileManager();
        candidateService = new CandidateServiceImpl();
        votingService = new VotingServiceImpl();
        auditService = new AuditServiceImpl();
        loadResults();
    }


    private void loadResults() {
        List<Result> results = fileManager.load();

        for(Result result : results) {
            repository.save(result);
        }
    }


    private void saveResults() {
        fileManager.save(repository.findAll());
    }



    @Override
    public List<Result> generateResult(String electionId)
            throws UserNotFoundException {

        repository.deleteByElectionId(electionId);

        List<Candidate> candidates = candidateService.getAllCandidates();
        List<Vote> votes = votingService.getAllVotes();

        Map<String,Integer> voteCountMap = new HashMap<>();

        for(Vote vote : votes) {
            if(vote.getElectionId().equals(electionId)) {
                String candidateId = vote.getCandidateId();
                voteCountMap.put(candidateId, voteCountMap.getOrDefault(candidateId, 0) + 1);
            }
        }

        int highestVote = -1;
        int secondHighestVote = -1;
        String winnerCandidateId = null;
        String runnerUpCandidateId = null;

        for(Candidate candidate : candidates) {
            int count = voteCountMap.getOrDefault(candidate.getCandidateId(), 0);
            if (count > highestVote) {
                secondHighestVote = highestVote;
                runnerUpCandidateId = winnerCandidateId;

                highestVote = count;
                winnerCandidateId = candidate.getCandidateId();
            }
            else if (count > secondHighestVote && count < highestVote) {
                secondHighestVote = count;
                runnerUpCandidateId = candidate.getCandidateId();
            }
        }

        List<Result> generatedResults = new ArrayList<>();

        for(Candidate candidate : candidates) {
            if(candidate.getElectionId().equals(electionId)) {

                Result result = new Result();

                result.setResultId(IDGenerator.generateId("RES"));
                result.setElectionId(electionId);
                result.setCandidateId(candidate.getCandidateId());
                result.setCandidateName(candidate.getCandidateName());
                result.setVoteCount(voteCountMap.getOrDefault(candidate.getCandidateId(), 0));
                result.setPosition(candidate.getPosition());

                if(candidate.getCandidateId().equals(winnerCandidateId)) {
                    result.setResultStatus(ResultStatus.WINNER.name());
                }
                else if(candidate.getCandidateId().equals(runnerUpCandidateId)) {
                    result.setResultStatus(ResultStatus.RUNNER_UP.name());
                }
                else {
                    result.setResultStatus(ResultStatus.PARTICIPATED.name());
                }
                repository.save(result);
                generatedResults.add(result);
            }
        }

        auditService.log(
                AuditModule.RESULT,
                AuditAction.GENERATE_RESULT,
                "Generating the results"
        );

        saveResults();
        return generatedResults;

    }


    @Override
    public Result getWinner(String electionId) throws UserNotFoundException {
        List<Vote> votes = votingService.getAllVotes();

        Map<String, Integer> voteCountMap = new HashMap<>();

        for(Vote vote : votes) {
            if(vote.getElectionId().equals(electionId)) {
                String candidateId = vote.getCandidateId();
                voteCountMap.put(
                        candidateId,
                        voteCountMap.getOrDefault(candidateId, 0) + 1
                );
            }
        }

        String winnerCandidateId = null;

        int highestVote = 0;

        for(String candidateId : voteCountMap.keySet()) {
            int count = voteCountMap.get(candidateId);
            if(count > highestVote) {
                highestVote = count;
                winnerCandidateId = candidateId;
            }
        }

        if(winnerCandidateId == null) {
            throw new UserNotFoundException("No Votes Found");
        }

        Candidate candidate = candidateService.searchCandidate(winnerCandidateId);

        Result result = new Result();

        result.setResultId(IDGenerator.generateId("RES"));
        result.setElectionId(electionId);
        result.setCandidateId(candidate.getCandidateId());
        result.setCandidateName(candidate.getCandidateName());
        result.setVoteCount(highestVote);
        result.setPosition(candidate.getPosition());
        result.setResultStatus(String.valueOf(ResultStatus.WINNER));

        auditService.log(
                AuditModule.RESULT,
                AuditAction.WINNER,
                "Winner : " + candidate.getCandidateName()
        );

        repository.save(result);

        return result;
    }

}
