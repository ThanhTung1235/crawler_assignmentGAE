package crawler.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import crawler.utility.EncryptString;

import java.util.Calendar;
import java.util.List;

@Entity
public class Article {
    @Id
    private String link;
    @Index
    private long categoryId;
    private String title;
    private String content;
    private List<String> thumbnail;
    private String author;
    private String description;
    private long sourceId;
    private String createdAtMLS;
    private String updatedAtMLS;
    private String deletedAtMLS;
    @Index
    private int status; // 0.pending | 1. indexed. | -1. deleted.

    public Article() {
        this.createdAtMLS = EncryptString.getCurrentTime();
        this.updatedAtMLS = EncryptString.getCurrentTime();
        this.setStatus(Status.PENDING);
    }


    public enum Status {
        PENDING(0), INDEXED(1), DELETED(-1);

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

    public String getLink() {
        return link;
    }

    public Article setLink(String link) {
        this.link = link;
        return this;
    }

    public List<String> getThumbnail() {
        return thumbnail;
    }

    public Article setThumbnail(List<String> thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Article setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Article setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCreatedAtMLS() {
        return createdAtMLS;
    }

    public Article setCreatedAtMLS(String createdAtMLS) {
        this.createdAtMLS = createdAtMLS;
        return this;
    }

    public String getUpdatedAtMLS() {
        return updatedAtMLS;
    }

    public Article setUpdatedAtMLS(String updatedAtMLS) {
        this.updatedAtMLS = updatedAtMLS;
        return this;
    }

    public String getDeletedAtMLS() {
        return deletedAtMLS;
    }

    public Article setDeletedAtMLS(String deletedAtMLS) {
        this.deletedAtMLS = deletedAtMLS;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Article setStatus(Status status) {
        this.status = status.getValue();
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Article setContent(String content) {
        this.content = content;
        return this;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public Article setSourceId(long sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public Article setCategoryId(long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    @Override
    public String toString() {
        return "Article{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
