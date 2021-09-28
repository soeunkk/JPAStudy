package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/* @Entity: JPA 에게 테이블임을 알림 */
@Entity
/* @Table(name): 매핑할 DB 테이블을 지정 */
//@Table(name="USER")
public class Member {
    /* @Id: PK 지정 */
    @Id
    private Long id;
    /* @Column(name): 매핑할 DB 컬럼을 지정 */
    //@Column(name="username")
    private String name;

    //Getter, Setter 단축키: Alt + Insert
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
