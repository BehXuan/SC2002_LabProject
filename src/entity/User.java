package src.entity;

public class User {
   private String userID;
   private String password;
   private String name;

   public User() {
   }

   public User(String userid, String pw, String name) {
      this.userID = userid;
      this.password = pw;
      this.name = name;
   }

   public void setUserId(String userid) {
      this.userID = userid;
   }

   public void setPassword(String pw) {
      this.password = pw;
   }

   public void setName(String name) {
      this.name = name;
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
