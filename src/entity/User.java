package src.entity;

abstract public class User {
   private String userID;
   private String password;
   private String name;
   private String email;

   public User() {
   }

   public User(String userid, String pw, String name, String email) {
      this.userID = userid;
      this.password = pw;
      this.name = name;
      this.email = email;
   }

   // Setter
   public void setUserId(String userid) {this.userID = userid;}
   public void setPassword(String pw) {this.password = pw;}
   public void setName(String name) {this.name = name;}
   public void setEmail(String email) {this.email = email;}

   // Getter
   public String getUserId() {return this.userID;}
   public String getPassword() {return this.password;}
   public String getName() {return this.name;}
   public String getEmail() {return this.email;}

   
   public String toString() {
      return "User{" +
            "userID='" + userID + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
   }
}
