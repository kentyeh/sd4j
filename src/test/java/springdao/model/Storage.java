package springdao.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kent Yeh
 */
@Entity
@Table(name = "storage")
public class Storage implements Serializable {

    private static final long serialVersionUID = 4119657693199960543L;
    @Id
    private Integer sid;
    private String location;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storage")
    private List<UserStore> userstoreList;

    public Storage() {
    }

    public Storage(Integer sid) {
        this.sid = sid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @XmlTransient
    public List<UserStore> getUserstoreList() {
        return userstoreList;
    }

    public void setUserstoreList(List<UserStore> userstoreList) {
        this.userstoreList = userstoreList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Storage other = (Storage) obj;
        if (this.sid != other.sid && (this.sid == null || !this.sid.equals(other.sid))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.sid != null ? this.sid.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", location, sid);
    }
}
