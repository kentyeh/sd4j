package springdao.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
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
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "member")
@SqlResultSetMapping(name = "memberMapping",
    entities = {
        @EntityResult(entityClass = Member.class, fields = {
            @FieldResult(name = "id", column = "id_L"),
            @FieldResult(name = "name", column = "name_S"),
            @FieldResult(name = "userType", column = "userType_E"),
            @FieldResult(name = "version", column = "version")
        })
    }, classes = {
        @ConstructorResult(targetClass = Member.class, columns = {
            @ColumnResult(name = "id_L", type = Long.class),
            @ColumnResult(name = "name_S", type = String.class),
            @ColumnResult(name = "userType_E", type = String.class),
            @ColumnResult(name = "version", type = java.util.Date.class)
        })
    })
@NamedQueries({
    @NamedQuery(name = "Member.findAll", query = "SELECT m FROM Member m"),
    @NamedQuery(name = "Member.findByName1", query = "SELECT m FROM Member m WHERE m.name like ?1 ORDER BY m.id"),
    @NamedQuery(name = "Member.findByName2", query = "SELECT m FROM Member m WHERE m.name like :name ORDER BY m.id")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "Member.findNativeAll",
            query = "select * from member ", resultClass = Member.class),
    @NamedNativeQuery(name = "Member.findNativeByName1",
            query = "select * from member m where m.name like ?1 ORDER BY m.id", resultClass = Member.class),
    @NamedNativeQuery(name = "Member.findNativeByName2",
            query = "select * from member m where m.name like :name ORDER BY m.id", resultClass = Member.class),
    @NamedNativeQuery(name = "Member.findMappingAll", query = "select id as id_L,name as name_S,userType as userType_E,version from member", resultSetMapping = "memberMapping"),
    @NamedNativeQuery(name = "Phone.findNativeAll", query = "select * from phone", resultClass = Phone.class),
    @NamedNativeQuery(name = "Phone.findNativeByNumber1", query = "select * from phone where phone= ?1", resultClass = Phone.class),
    @NamedNativeQuery(name = "Phone.findNativeByNumber2", query = "select * from phone where phone= :phone", resultClass = Phone.class)
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
    @Temporal(TemporalType.TIMESTAMP)
    @Version
    private @Getter @Setter java.util.Date version;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "contactbook",
            joinColumns = {
                @JoinColumn(name = "oid", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "cid", referencedColumnName = "id")})
    private @Getter @Setter Set<Member> contacts;
    /**
     * if orphanRemoval=true is specified, CascadeType.REMOVE is redundant.
     * <a href="http://www.objectdb.com/java/jpa/persistence/delete#Orphan_Removal_">see
     * also.</a>
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
    /**
     * Constructor for {@link SqlResultSetMapping SqlResultSetMapping}
     * @param id
     * @param name
     * @param userType 
     */
    public Member(Long id, String name,String userType,java.util.Date version) {
        this.id = id;
        this.name = name;
        this.userType=SupplyChainMember.valueOf(userType);
        this.version = version;
    }

    @Override
    public String toString() {
        return String.format("%s[%s] is %s", name, id, userType);
    }
}
