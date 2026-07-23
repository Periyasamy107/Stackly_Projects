package repository;

import interfaces.Repository;
import model.Result;

public interface ResultRepository extends Repository<Result> {

    void deleteByElectionId(String electionId);

}
