package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id @GeneratedValue
    @Column(name="PRODUCT_ID")
    private long id;

    private String name;

    //다대다 연관관계 양방향
    /*
    @ManyToMany(mappedBy = "products")
    private List<Member> members = new ArrayList<>();
     */
    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
