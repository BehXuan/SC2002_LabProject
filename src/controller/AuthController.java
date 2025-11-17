package src.controller;

public interface AuthController{
    boolean login(String userName, String pw);
    void logout();
    boolean updatePassword(String oldPW, String newPW);
}

