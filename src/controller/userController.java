package src.controller;
import src.DataStore;
import src.entity.User;


public class UserController {
    private User currentUser;
    private DataStore dataStore;

    public UserController(){
        this.dataStore = new DataStore();
    }

    public void login(){}//if can login, currentUser = user, else null
    public void logout(){currentUser = null;}
    public User getCurrentUser(){
        return currentUser;
    }
    public boolean updatePassword(){}
}
