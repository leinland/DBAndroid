package am.aua.com.dbproject.Utils;

import java.io.Serializable;
import java.util.Date;

public class BlackListItem implements Serializable {
    private String name;
    private long endDate;
    private int id;
    private String email;

    public BlackListItem(String name, long endDate, int id, String email) {
        this.name = name;
        this.endDate = endDate;
        this.id = id;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
