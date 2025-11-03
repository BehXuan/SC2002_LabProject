package src.entity;

public class User {
   private String userID;
   private String password;
   private String name;

   public User() {
   }

   public User(String var1, String var2, String var3) {
      this.userID = var1;
      this.password = var2;
      this.name = var3;
   }

   public void setUserId(String var1) {
      this.userID = var1;
   }

   public void setPassword(String var1) {
      this.password = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public String getUserId() {
      return this.userID;
   }

   public String getPassword() {
      return this.password;
   }

   public String getName() {
      return this.name;
   }
}
