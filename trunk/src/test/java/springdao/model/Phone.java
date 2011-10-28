package springdao.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 *
 * @author Kent Yeh
 */
@Entity
@Table(name = "phone")
public class Phone implements Serializable {

    private static final long serialVersionUID = 470086049496896194L;
    @Id
    private String phone;
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Member owner;

    public Phone() {
    }

    public Phone(Member owner, String phone) {
        this.phone = phone;
        this.owner = owner;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Phone other = (Phone) obj;
        if ((this.phone == null) ? (other.phone != null) : !this.phone.equals(other.phone)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.phone == null ? 0 : this.phone.hashCode();
    }

    @Override
    public String toString() {
        return phone;
    }
}