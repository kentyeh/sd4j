package springdao.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
@EqualsAndHashCode(of = {"id","phone"}, callSuper = false)
@ToString(includeFieldNames = false, of = {"id", "phone", "owner"})
public class Phone implements Serializable {

    private static final long serialVersionUID = 470086049496896194L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter String id;
    private @NotNull @Getter @Setter String phone;
    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "id")
    private @Getter @Setter Member owner;
}
