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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "member")
@NamedQueries(
        @NamedQuery(name = "Member.findByName", query = "SELECT m FROM Member m WHERE m.name like ?1 ORDER BY m.id"))
@NamedNativeQueries({
    @NamedNativeQuery(name = "Member.findNativeByName",
            query = "select * from member m where m.name like ?1 ORDER BY m.id",
            resultClass = Member.class)
})
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
public class Member implements Serializable {

    private static final long serialVersionUID = 1191330943030660967L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter Long id;
    @Size(max = 20)
    @NotNull
    private @Getter @Setter String name;
    @Column(name = "userType")
    @Enumerated(EnumType.STRING)
    @NotNull
    private @Getter @Setter SupplyChainMember userType = SupplyChainMember.C;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "contactbook",
            joinColumns = {
                @JoinColumn(name = "oid", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "cid", referencedColumnName = "id")})
    private @Getter @Setter Set<Member> contacts;
    /**
     * if orphanRemoval=true is specified, CascadeType.REMOVE is redundant. 
     * <a href="http://www.objectdb.com/java/jpa/persistence/delete#Orphan_Removal_">see also.</a>
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.LAZY)
    private @Getter @Setter List<UserStore> userstores;
    //It's very important to assign "cascade" attribute,without it the collection not to be saved.
    //重要:沒有指定"cascade"時，collection不會儲存
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
//    @OrderColumn(name = "phone")
    private @Getter @Setter Set<Phone> phones;

    public Member(Long id) {
        this.id = id;
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s[%s] is %s", name, id, userType);
    }
}
