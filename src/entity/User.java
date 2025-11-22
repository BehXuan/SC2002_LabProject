package src.entity;

/**
 * Abstract base class representing a user in the system.
 *
 * <p>This class defines common attributes and behaviors shared by all
 * user types: students, company representatives, and career center staff.
 * Concrete user types extend this class to inherit core user functionality.
 */
abstract public class User {
   private String userID;
   private String password;
   private String name;
   private String email;

   /**
    * Constructs an empty User with default values.
    */
   public User() {
   }

   /**
    * Constructs a User with the specified credentials and information.
    *
    * @param userid the unique user identifier
    * @param pw the user's password
    * @param name the user's full name
    * @param email the user's email address
    */
   public User(String userid, String pw, String name, String email) {
      this.userID = userid;
      this.password = pw;
      this.name = name;
      this.email = email;
   }

   // Setter

   /**
    * Sets the user id.
    *
    * @param userid the unique user identifier to set
    */
   public void setUserId(String userid) {this.userID = userid;}

   /**
    * Sets the user's password.
    *
    * @param pw the password to set
    */
   public void setPassword(String pw) {this.password = pw;}

   /**
    * Sets the user's name.
    *
    * @param name the full name to set
    */
   public void setName(String name) {this.name = name;}

   /**
    * Sets the user's email address.
    *
    * @param email the email address to set
    */
   public void setEmail(String email) {this.email = email;}

   // Getter

   /**
    * Gets the user id.
    *
    * @return the unique user identifier
    */
   public String getUserId() {return this.userID;}

   /**
    * Gets the user's password.
    *
    * @return the password
    */
   public String getPassword() {return this.password;}

   /**
    * Gets the user's name.
    *
    * @return the full name
    */
   public String getName() {return this.name;}

   /**
    * Gets the user's email address.
    *
    * @return the email address
    */
   public String getEmail() {return this.email;}

   /**
    * Returns a string representation of the User.
    *
    * @return a string containing the user id, name, and email
    */
   public String toString() {
      return "User{" +
            "userID='" + userID + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
   }
}
