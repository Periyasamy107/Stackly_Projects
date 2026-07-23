package repository;

import interfaces.Repository;
import model.Election;

public interface ElectionRepository extends Repository<Election> {

    Election findActiveElection();

}
