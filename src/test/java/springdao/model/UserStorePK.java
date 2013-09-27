package springdao.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Kent
 */
@Embeddable
public class UserStorePK implements Serializable {

    private static final long serialVersionUID = 1794742527018289345L;
    @Column(name = "id")
    private long ownerId;
    @Column(name = "sid")
    private int soreageId;
    private String goods;

    public UserStorePK() {
    }

    public UserStorePK(long ownerId, int soreageId, String goods) {
        this.ownerId = ownerId;
        this.soreageId = soreageId;
        this.goods = goods;
    }

    public UserStorePK(Member owner, Storage storage, String goods) {
        this(owner.getId(), storage.getSid(), goods);
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public int getSoreageId() {
        return soreageId;
    }

    public void setSoreageId(int soreageId) {
        this.soreageId = soreageId;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserStorePK other = (UserStorePK) obj;
        if (this.ownerId != other.ownerId) {
            return false;
        }
        if (this.soreageId != other.soreageId) {
            return false;
        }
        if ((this.goods == null) ? (other.goods != null) : !this.goods.equals(other.goods)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (int) (this.ownerId ^ (this.ownerId >>> 32));
        hash = 89 * hash + this.soreageId;
        hash = 89 * hash + (this.goods != null ? this.goods.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "ossohe.test.model.UserstorePK[ id=" + ownerId + ", sid=" + soreageId + ", goods=" + goods + " ]";
    }
}
