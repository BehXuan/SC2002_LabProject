
package src.controller;

import src.entity.*;
import src.DataStore;

public class CompanyRepresentativeController implements AuthController{
    private CompanyRepresentative currentRep;
    private DataStore dataStore;

    public CompanyRepresentativeController() {
        this.dataStore = DataStore.getInstance();
    }

    public void setCurrentCompanyRepresentative(CompanyRepresentative c) {
        this.currentRep = c;
    }

    public CompanyRepresentative getCurrentCompayRepresentative() {
        return currentRep;
    }

    @Override
    public boolean login(String userName, String pw) {
        // check the userName and pw against dataStore
        for (CompanyRepresentative c : dataStore.getCompanyRepresentativeList()) {
            if (c.getUserId().equals(userName) && c.getPassword().equals(pw)) {
                setCurrentCompanyRepresentative(c);
                return true;
            }
        }
        return false;
    }

    @Override
    public void logout() {
        setCurrentCompanyRepresentative(null);
    }
    
    @Override
    public boolean updatePassword(String oldPW, String newPW) {
        if (getCurrentCompayRepresentative() == null) {
            return false;
        }

        if (getCurrentCompayRepresentative().getPassword().equals(oldPW)) {
            getCurrentCompayRepresentative().setPassword(newPW);
            return true;
        }
        return false;
    }
}