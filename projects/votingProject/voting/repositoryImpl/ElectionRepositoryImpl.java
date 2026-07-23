package repositoryImpl;

import model.Election;
import repository.ElectionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectionRepositoryImpl implements ElectionRepository {

    private Map<String, Election> electionMap;

    public ElectionRepositoryImpl() {
        electionMap = new HashMap<>();
    }

    @Override
    public boolean save(Election election) {
        if(electionMap.containsKey(election.getElectionId())) {
            return false;
        }
        electionMap.put(election.getElectionId(), election);
        return true;
    }

    @Override
    public boolean update(Election election) {
        if(!electionMap.containsKey(election.getElectionId())) {
            return false;
        }
        electionMap.put(election.getElectionId(), election);
        return true;
    }

    @Override
    public boolean delete(String id) {
        if(!electionMap.containsKey(id)) {
            return false;
        }
        electionMap.remove(id);
        return true;
    }

    @Override
    public Election findById(String id) {
        return electionMap.get(id);
    }

    @Override
    public List<Election> findAll() {
        return new ArrayList<>(electionMap.values());
    }

    @Override
    public boolean exists(String id) {
        return electionMap.containsKey(id);
    }

    @Override
    public Election findActiveElection() {
        for(Election election : electionMap.values()) {
            if(election.getStatus().equalsIgnoreCase("ACTIVE")) {
                return election;
            }
        }
        return null;
    }

}
