package springdao.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Kent Yeh
 */
@Entity
@Table(name = "phone")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "phone", callSuper = false)
@ToString(includeFieldNames = false, of = "phone")
public class Phone implements Serializable {

    private static final long serialVersionUID = 470086049496896194L;
    @Id
    private @Getter @Setter String phone;
    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private @Getter @Setter Member owner;
}
