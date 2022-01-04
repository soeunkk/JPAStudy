package hellojpa;

import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    /*
    //다대일 연관관계 단방향
    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;
     */

    //일대다 연관관계 양방향
    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)  //@JoinColumn을 했기 때문에 연관관계의 주인이 됨 -> DB가 변형되는 것을 막고자 추가, 업데이트 하는 것을 막음 (결과적으로 읽기 전용으로 만듦)
    private Team team;

    //일대일 연관관계 단방향
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    //다대다 연관관계 단방향
    /*
    @ManyToMany
    @JoinTable(name="MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();
     */
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
