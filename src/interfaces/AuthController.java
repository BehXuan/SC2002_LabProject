package src.interfaces;
import src.enums.LoginResult;

public interface AuthController{
    LoginResult login(String userName, String pw);
    void logout();
    boolean updatePassword(String oldPW, String newPW);
}

