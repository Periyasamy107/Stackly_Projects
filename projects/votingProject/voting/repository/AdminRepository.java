package repository;

import interfaces.Repository;
import model.Admin;

public interface AdminRepository extends Repository<Admin> {

    boolean existsByUsername(String username);

    Admin findByUsername(String username);

}