package repositoryImpl;

import model.Vote;
import repository.VoteRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteRepositoryImpl implements VoteRepository {

    private Map<String, Vote> voteMap;

    public VoteRepositoryImpl() {
        voteMap = new HashMap<>();
    }

    @Override
    public boolean save(Vote vote) {
        if(voteMap.containsKey(vote.getVoteId())) {
            return false;
        }
        voteMap.put(vote.getVoteId(), vote);
        return true;
    }

    @Override
    public boolean update(Vote vote) {
        if(!voteMap.containsKey(vote.getVoteId())) {
            return false;
        }
        voteMap.put(vote.getVoteId(), vote);
        return true;
    }

    @Override
    public boolean delete(String voteId) {
        if(!voteMap.containsKey(voteId)) {
            return  false;
        }
        voteMap.remove(voteId);
        return true;
    }

    @Override
    public Vote findById(String voteId) {
        return voteMap.get(voteId);
    }

    @Override
    public List<Vote> findAll() {
        return new ArrayList<>(voteMap.values());
    }

    @Override
    public boolean exists(String voteId) {
        return voteMap.containsKey(voteId);
    }

    @Override
    public boolean existsByStudentAndElection(String studentId, String electionId) {
        for(Vote vote : voteMap.values()){
            if(vote.getStudentId().equals(studentId) && vote.getElectionId().equals(electionId)) {
                return true;
            }
        }
        return false;
    }

}
