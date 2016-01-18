package springdao.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Kent Yeh
 */
@Entity
@Table(name = "userstore")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
public class UserStore implements Serializable {

    private static final long serialVersionUID = -1004295437748921023L;
    @EmbeddedId
    protected @Getter @Setter UserStorePK userstorePK;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private @Getter @Setter Member owner;
    @JoinColumn(name = "sid", referencedColumnName = "sid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private @Getter @Setter Storage storage;

    public UserStore(UserStorePK userstorePK) {
        this.userstorePK = userstorePK;
    }

    public UserStore(long id, int sid, String goods) {
        this.userstorePK = new UserStorePK(id, sid, goods);
    }

    public String getGoods() {
        return userstorePK.getGoods();
    }

    @Override
    public String toString() {
        return String.format("%s's %s @ %s", owner.getName(), getGoods(), getStorage().getLocation());
    }
}
