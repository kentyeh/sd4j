package springdao.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Kent
 */
@Embeddable
@EqualsAndHashCode(of = {"ownerId", "soreageId", "goods"}, callSuper = false)
@NoArgsConstructor
public class UserStorePK implements Serializable {

    private static final long serialVersionUID = 1794742527018289345L;
    @Column(name = "id")
    private @Getter @Setter long ownerId;
    @Column(name = "sid")
    private @Getter @Setter int soreageId;
    private @Getter @Setter String goods;

    public UserStorePK(long ownerId, int soreageId, String goods) {
        this.ownerId = ownerId;
        this.soreageId = soreageId;
        this.goods = goods;
    }

    public UserStorePK(Member owner, Storage storage, String goods) {
        this(owner.getId(), storage.getSid(), goods);
    }

    @Override
    public String toString() {
        return "ossohe.test.model.UserstorePK[ id=" + ownerId + ", sid=" + soreageId + ", goods=" + goods + " ]";
    }
}
