package service;

import enums.AuditAction;
import enums.AuditModule;
import exception.*;
import file.AdminFileManager;
import interfaces.AdminService;
import interfaces.AuditService;
import model.Admin;
import repository.AdminRepository;
import repositoryImpl.AdminRepositoryImpl;
import util.IDGenerator;
import util.PasswordUtil;
import validation.ValidationUtil;

import  java.security.NoSuchAlgorithmException;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;
    private AdminFileManager adminFileManager;
    private AuditService auditService;

    public AdminServiceImpl() {
        adminRepository = new AdminRepositoryImpl();
        adminFileManager = new AdminFileManager();
        auditService = new AuditServiceImpl();
        loadAdmins();
    }



    private void loadAdmins() {
        List<Admin> admins = adminFileManager.load();

        for(Admin admin : admins) {
            adminRepository.save(admin);
        }
    }



    private void saveAdmins() {
        adminFileManager.save(adminRepository.findAll());
    }




    public void registerAdmin(Admin admin)
            throws UserAlreadyExistsException, InputValidationException, NoSuchAlgorithmException {

        validateAdmin(admin);

        admin.setJoiningDate(java.time.LocalDate.now());

        admin.setAdminId(IDGenerator.generateId("ADM"));

        if(adminRepository.exists(admin.getAdminId())) {
            throw new UserAlreadyExistsException("Admin already exists.");
        }


        if(adminRepository.existsByUsername(admin.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists.");
        }

        if(ValidationUtil.isValidMobile(admin.getMobileNumber())) {
            admin.setMobileNumber(admin.getMobileNumber());
        } else {
            throw new InputValidationException("Please enter a valid mobile number!!");
        }

        if(ValidationUtil.isValidPassword(admin.getPassword())) {
            admin.setPassword(admin.getPassword());
        } else {
            throw new InputValidationException("Password minimum length is 3.");
        }

        admin.setPasswordHash(PasswordUtil.encrypt(admin.getPasswordHash()));

        boolean status = adminRepository.save(admin);

        if(status) {

            auditService.log(
                    AuditModule.ADMIN,
                    AuditAction.ADD,
                    "Admin Added : " + admin.getUsername()
            );
            saveAdmins();
        }
    }


    private void validateAdmin(Admin admin) throws InputValidationException {

        if(admin == null) {
            throw new InputValidationException("Admin cannot be null.");
        }

        if(ValidationUtil.isEmpty(admin.getUsername())) {
            throw new InputValidationException("Username required.");
        }

        if(ValidationUtil.isEmpty(admin.getPasswordHash())) {
            throw new InputValidationException("Password required.");
        }

        if(!ValidationUtil.isValidEmail(admin.getEmail())) {
            throw new InputValidationException("Invalid Email.");
        }
    }



    public void updateAdmin(Admin admin) throws UserNotFoundException, InputValidationException {

        validateAdmin(admin);

        if(!adminRepository.exists(admin.getAdminId())) {
            throw new UserNotFoundException("Admin not found.");
        }

        boolean status = adminRepository.update(admin);

        if(status) {
            auditService.log(
                    AuditModule.ADMIN,
                    AuditAction.UPDATE,
                    "Admin Updated : " + admin.getUsername()
            );
            saveAdmins();
        }
    }


    @Override
    public void deleteAdmin(String adminId) throws UserNotFoundException {
        if(!adminRepository.exists(adminId)) {
            throw new UserNotFoundException("Admin not found.");
        }

        boolean status = adminRepository.delete(adminId);

        if(status) {

            auditService.log(
                    AuditModule.ADMIN,
                    AuditAction.DELETE,
                    "Admin Removed."
            );
            saveAdmins();
        }
    }


    @Override
    public Admin searchAdmin(String adminId) throws UserNotFoundException {
        Admin admin = adminRepository.findById(adminId);
        if(admin == null) {
            throw new UserNotFoundException("Admin not found.");
        }
        return admin;
    }


    @Override
    public Admin searchAdminByName(String adminName) throws UserNotFoundException {
        Admin admin = adminRepository.findByUsername(adminName);
        if(admin == null) {
            throw new UserNotFoundException("Admin not found.");
        }
        return admin;
    }


    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }


}
