package interfaces;


import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import model.Admin;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface AdminService {

    void registerAdmin(Admin admin)
            throws UserAlreadyExistsException,
            InputValidationException, NoSuchAlgorithmException;

    void updateAdmin(Admin admin)
            throws UserNotFoundException,
            InputValidationException;

    void deleteAdmin(String adminId)
            throws UserNotFoundException;

    Admin searchAdmin(String adminId)
            throws UserNotFoundException;

    Admin searchAdminByName(String adminName)
            throws UserNotFoundException;

    List<Admin> getAllAdmins();


}
