package repositoryImpl;

import model.Result;
import repository.ResultRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultRepositoryImpl implements ResultRepository {

    private Map<String, Result> resultMap;

    public ResultRepositoryImpl() {
        resultMap = new HashMap<>();
    }

    @Override
    public boolean save(Result result) {

        if(resultMap.containsKey(result.getResultId())) {
            return false;
        }

        resultMap.put(result.getResultId(), result);

        return  true;
    }


    @Override
    public boolean update(Result result) {

        if(!resultMap.containsKey(result.getResultId())) {
            return false;
        }

        resultMap.put(result.getResultId(), result);

        return true;
    }


    @Override
    public boolean delete(String id) {
        if(!resultMap.containsKey(id)) {
            return false;
        }
        resultMap.remove(id);
        return true;
    }


    @Override
    public Result findById(String id) {
        return resultMap.get(id);
    }


    @Override
    public List<Result> findAll() {
        return new ArrayList<>(resultMap.values());
    }

    @Override
    public boolean exists(String id) {
        return resultMap.containsKey(id);
    }

    @Override
    public void deleteByElectionId(String electionId) {
        resultMap.values().removeIf(result -> result.getElectionId().equals(electionId));
    }

}
