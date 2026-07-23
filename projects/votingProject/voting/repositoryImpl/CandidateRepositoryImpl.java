package repositoryImpl;

import model.Candidate;
import repository.CandidateRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandidateRepositoryImpl implements CandidateRepository {

    private Map<String, Candidate>  candidateMap;

    public CandidateRepositoryImpl() {
        candidateMap = new HashMap<>();
    }

    @Override
    public boolean save(Candidate candidate) {
        if(candidateMap.containsKey(candidate.getCandidateId())) {
            return false;
        }
        candidateMap.put(candidate.getCandidateId(), candidate);
        return true;
    }


    @Override
    public boolean update(Candidate candidate) {
        if(!candidateMap.containsKey(candidate.getCandidateId())) {
            return false;
        }
        candidateMap.put(candidate.getCandidateId(), candidate);
        return true;
    }


    @Override
    public boolean delete(String id) {
        if(!candidateMap.containsKey(id)) {
            return false;
        }
        candidateMap.remove(id);
        return true;
    }


    @Override
    public Candidate findById(String id) {
        return candidateMap.get(id);
    }


    @Override
    public List<Candidate> findAll() {
        return new ArrayList<>(candidateMap.values());
    }


    @Override
    public boolean exists(String id) {
        return candidateMap.containsKey(id);
    }


    @Override
    public boolean existsByStudentId(String id) {
        for(Candidate candidate : candidateMap.values()) {
            if(candidate.getStudentId().equals(id)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Candidate findByStudentId(String id) {
        for(Candidate candidate : candidateMap.values()) {
            if(candidate.getStudentId().equals(id)) {
                return candidate;
            }
        }
        return null;
    }

}
