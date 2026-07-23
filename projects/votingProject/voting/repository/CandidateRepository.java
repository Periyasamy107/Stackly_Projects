package repository;

import interfaces.Repository;
import model.Candidate;

public interface CandidateRepository extends Repository<Candidate> {

    boolean existsByStudentId(String studentId);

    Candidate findByStudentId(String studentId);

}
