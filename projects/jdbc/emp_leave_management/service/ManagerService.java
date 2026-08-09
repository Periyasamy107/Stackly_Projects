package service;

import dao.ManagerDAO;
import model.Manager;

import java.util.List;

public class ManagerService {

    private ManagerDAO managerDAO;

    public ManagerService () {
        managerDAO = new ManagerDAO();
    }

    public boolean registerManager(Manager manager) {
        if(managerDAO.managerIdExists(manager.getManagerId())) {
            System.out.println("\nManager ID already exists.\n");
            return false;
        }
        if(managerDAO.emailExists(manager.getEmail())) {
            System.out.println("\nManager Email already present.\n");
            return false;
        }
        return managerDAO.addManager(manager);
    }


    public List<Manager> getAllManagers() {
        return managerDAO.getAllManagers();
    }


    public Manager getManagerById(String managerId) {
        return managerDAO.getManagerById(managerId);
    }


    public boolean updateManager(Manager manager) {
        if (!managerDAO.managerIdExists(manager.getManagerId())) {
            System.out.println("Manager not found.");
            return false;
        }
        return managerDAO.updateManager(manager);
    }


    public boolean deactivatedManager(String managerId) {
        if (!managerDAO.managerIdExists(managerId)) {
            System.out.println("Manager not found.");
            return false;
        }
        return managerDAO.deactivatedManager(managerId);
    }



}
