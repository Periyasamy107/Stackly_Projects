package repositoryImpl;

import model.Admin;
import repository.AdminRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminRepositoryImpl implements AdminRepository {

    private Map<String, Admin> adminMap;

    public AdminRepositoryImpl() {
        adminMap = new HashMap<>();
    }


    public boolean save(Admin admin) {
        if(adminMap.containsKey(admin.getAdminId())) {
            return false;
        }
        adminMap.put(admin.getAdminId(), admin);
        return true;
    }


    public boolean update(Admin admin) {
        if(!adminMap.containsKey(admin.getAdminId())) {
            return false;
        }
        adminMap.put(admin.getAdminId(), admin);
        return true;
    }


    public boolean delete(String id) {
        if(!adminMap.containsKey(id)) {
            return false;
        }
        adminMap.remove(id);
        return true;
    }


    public Admin findById(String id) {
        return adminMap.get(id);
    }


    public List<Admin> findAll() {
        return new ArrayList<>(adminMap.values());
    }


    public boolean exists(String id) {
        return adminMap.containsKey(id);
    }


    public boolean existsByUsername(String username) {
        for(Admin admin : adminMap.values()) {
            if(admin.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }


    public Admin findByUsername(String username) {
        for(Admin admin : adminMap.values()) {
            if(admin.getUsername().equalsIgnoreCase(username)) {
                return admin;
            }
        }
        return null;
    }


}
