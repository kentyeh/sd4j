package springdao.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Table(name = "supervisor")
@EqualsAndHashCode(of = "account", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "account", includeFieldNames = false)
public class Admin implements Serializable {

    private static final long serialVersionUID = 3131273870771882953L;
    @Id @Getter
    private String account;
    @NotNull @Getter @Setter @Size(max = 20)
    private String password;
}
