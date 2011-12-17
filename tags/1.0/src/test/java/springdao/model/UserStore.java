package springdao.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Kent Yeh
 */
@Entity
@Table(name = "userstore")
public class UserStore implements Serializable {

    @EmbeddedId
    protected UserStorePK id;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Member owner;
    @JoinColumn(name = "sid", referencedColumnName = "sid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Storage storage;

    public UserStore() {
    }

    public UserStore(UserStorePK userstorePK) {
        this.id = userstorePK;
    }

    public UserStore(long id, int sid, String goods) {
        this.id = new UserStorePK(id, sid, goods);
    }

    public UserStorePK getUserstorePK() {
        return id;
    }

    public void setUserstorePK(UserStorePK userstorePK) {
        this.id = userstorePK;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public String getGoods() {
        return id.getGoods();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserStore other = (UserStore) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%s's %s @ %s", owner.getName(), getGoods(), getStorage().getLocation());
    }
}
