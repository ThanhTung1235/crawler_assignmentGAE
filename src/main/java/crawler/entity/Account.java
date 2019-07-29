package crawler.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import crawler.utility.EncryptString;

@Entity
public class Account {
    @Id
    private String username;
    @Index
    private String password;
    @Index
    private String salt;
    private String createdAt;
    private String updatedAt;
    @Index
    private int status;
    @Index
    private int role;

    public Account() {
        generateSalt();
        this.createdAt = EncryptString.getCurrentTime();
        this.updatedAt = EncryptString.getCurrentTime();
        this.setStatus(Status.ACTIVE);
        this.setRole(Role.MEMBER);
    }

    private void generateSalt() {
        this.salt = EncryptString.generateSalt();
    }

    public Account encryptPassword(String password) throws Exception {
        this.password = EncryptString.hashPassword(password) + this.salt;
        return this;
    }

    public enum Status {
        ACTIVE(1), DEACTIVE(0), DELETE(-1);

        int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Status findByValue(int value) {
            for (Status status : Status.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            return null;
        }
    }

    public enum Role {
        MEMBER(0), ADMIN(1);

        int value;

        Role(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Role findByValue(int value) {
            for (Role role : Role.values()) {
                if (role.getValue() == value) {
                    return role;
                }
            }
            return null;
        }
    }

    public String getUsername() {
        return username;
    }

    public Account setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Account setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public Account setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Account setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Account setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Account setStatus(Status status) {
        this.status = status.getValue();
        return this;
    }

    public int getRole() {
        return role;
    }

    public Account setRole(Role role) {
        this.role = role.getValue();
        return this;
    }
}
