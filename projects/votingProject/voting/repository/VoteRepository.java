package repository;

import interfaces.Repository;
import model.Vote;

public interface VoteRepository extends Repository<Vote> {

    boolean existsByStudentAndElection(String studentId, String electionId);

}
