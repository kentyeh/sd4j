package springdao.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "member")
@NamedQueries(
@NamedQuery(name = "Member.findByName", query = "SELECT m FROM Member m WHERE m.name like ?1 ORDER BY m.id"))
@NamedNativeQueries({
    @NamedNativeQuery(name = "Member.findNativeByName",
    query = "select * from member m where m.name like ?1 ORDER BY m.id",
    resultClass = Member.class)
})
@Entity
public class Member implements Serializable {

    private static final long serialVersionUID = 1191330943030660967L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 20)
    @NotNull
    private String name;
    @Column(name = "userType")
    @Enumerated(EnumType.STRING)
    @NotNull
    private SupplyChainMember userType = SupplyChainMember.C;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "contactbook",
    joinColumns = {
        @JoinColumn(name = "oid", referencedColumnName = "id")},
    inverseJoinColumns = {
        @JoinColumn(name = "cid", referencedColumnName = "id")})
    private Set<Member> contacts;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<UserStore> userstores;
    //It's very important to assign "cascade" attribute,without it the collection not to be saved.
    //重要:沒有指定"cascade"時，collection不會儲存
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
//    @OrderColumn(name = "phone")
    private Set<Phone> phones;

    public Member() {
    }

    public Member(Long id) {
        this.id = id;
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupplyChainMember getUserType() {
        return userType;
    }

    public void setUserType(SupplyChainMember userType) {
        this.userType = userType;
    }

    public Set<Member> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Member> contacts) {
        this.contacts = contacts;
    }

    public List<UserStore> getUserstores() {
        return userstores;
    }

    public void setUserstores(List<UserStore> userstores) {
        this.userstores = userstores;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Member other = (Member) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%s[%s] is %s", name, id, userType);
    }
}
