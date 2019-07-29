package crawler.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import crawler.utility.EncryptString;

@Entity
public class Category {
    @Id
    private long Id;
    @Index
    private String Name;
    @Index
    private int status;
    private String createdAt;
    private String updatedAt;
    private String deleteddAt;

    public Category() {
        this.createdAt = EncryptString.getCurrentTime();
        this.updatedAt = EncryptString.getCurrentTime();
        this.setStatus(Category.Status.ACTIVE);
    }

    public long getId() {
        return Id;
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

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status.getValue();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeleteddAt() {
        return deleteddAt;
    }

    public void setDeleteddAt(String deleteddAt) {
        this.deleteddAt = deleteddAt;
    }
}
