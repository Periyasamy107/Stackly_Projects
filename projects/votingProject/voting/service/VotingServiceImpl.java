package service;

import enums.AuditAction;
import enums.AuditModule;
import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import file.VoteFileManager;
import interfaces.AuditService;
import interfaces.VotingService;
import model.Vote;
import repository.VoteRepository;
import repositoryImpl.VoteRepositoryImpl;
import validation.ValidationUtil;

import java.util.List;

public class VotingServiceImpl implements VotingService {

    private VoteRepository voteRepository;
    private VoteFileManager voteFileManager;
    private AuditService auditService;

    public VotingServiceImpl() {
        voteRepository = new VoteRepositoryImpl();
        voteFileManager = new VoteFileManager();
        auditService = new AuditServiceImpl();
        loadVotes();
    }

    private void loadVotes() {
        List<Vote> votes = voteFileManager.load();
        for(Vote vote : votes) {
            voteRepository.save(vote);
        }
    }

    private void saveVotes() {
        voteFileManager.save(voteRepository.findAll());
    }

    @Override
    public boolean castVote(Vote vote) throws
            UserAlreadyExistsException, UserNotFoundException, InputValidationException {
        validateVote(vote);

        if(voteRepository.exists(vote.getVoteId())) {
            throw new UserAlreadyExistsException("Vote already exists");
        }

        if(voteRepository.existsByStudentAndElection(
                vote.getStudentId(),
                vote.getElectionId()
        )) {
            throw new UserAlreadyExistsException("Student already voted");
        }

        boolean status = voteRepository.save(vote);

        if(status) {
            auditService.log(
                    AuditModule.VOTING,
                    AuditAction.CAST_VOTE,
                    "Cast Vote : " + vote.getVoteId()
            );
            saveVotes();
        }

        return status;
    }


    private void validateVote(Vote vote) throws InputValidationException {
        if(vote == null) {
            throw  new InputValidationException("Vote cannot be null");
        }

        if(ValidationUtil.isEmpty(vote.getStudentId())) {
            throw new InputValidationException("Student ID required");
        }

        if(ValidationUtil.isEmpty(vote.getCandidateId())) {
            throw  new InputValidationException("Candidate ID required");
        }
    }

    @Override
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }


    @Override
    public boolean checkStudentVoted(String studentId, String electionId) {
        return voteRepository.existsByStudentAndElection(studentId, electionId);
    }

}
