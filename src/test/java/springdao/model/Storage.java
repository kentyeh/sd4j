package springdao.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Kent Yeh
 */
@Entity
@Table(name = "storage")

@NoArgsConstructor
@EqualsAndHashCode(of = "sid", callSuper = false)
public class Storage implements Serializable {

    private static final long serialVersionUID = 4119657693199960543L;
    @Id
    private @Getter @Setter Integer sid;
    private @Getter @Setter String location;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storage")
    private @Getter(onMethod=@__({@XmlTransient})) @Setter List<UserStore> userstoreList;

    public Storage(Integer sid) {
        this.sid = sid;
    }
    
    @Override
    public String toString() {
        return String.format("%s[%s]", location, sid);
    }
}
