package src.controller;

import src.DataStore;
import src.entity.User;

public class UserController {
    private User currentUser;
    private DataStore dataStore;

    public UserController() {
        this.dataStore = new DataStore();
    }

    public void setCurrentUser(User u) {
        this.currentUser = u;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean login(String userName, String pw) {
        // check the userName and pw against dataStore
        for (User u : dataStore.getStudentList()) {
            if (u.getName() == userName && u.getPassword() == pw) {
                setCurrentUser(u);
                return true;
            }
        }
        for (User u : dataStore.getCareerCenterStaffList()) {
            if (u.getName() == userName && u.getPassword() == pw) {
                setCurrentUser(u);
                return true;
            }
        }
        for (User u : dataStore.getCompanyRepresentativeList()) {
            if (u.getName().equals(userName) && u.getPassword().equals(pw)) {
                setCurrentUser(u);
                return true;
            }
        }
        return false;
    }

    public void logout() {
        setCurrentUser(null);
    }

    public boolean updatePassword(String oldPW, String newPW) {
        if (getCurrentUser() == null) {
            return false;
        }

        if (getCurrentUser().getPassword().equals(oldPW)) {
            getCurrentUser().setUserId(newPW);
            return true;
        }
        return false;
    }
}
