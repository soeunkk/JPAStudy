package hellojpa;

import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name="TEAM_ID")
//    private Long teamId;

    //한 멤버는 하나의 팀만 선택할 수 있다고 가정
    //멤버 입장에서는 자신이 N이고 팀이 1인 관계임 (ManyToOne)
    @ManyToOne
    //JPA 에서는 객체지향으로 설계하나, DB 에서 사용할 수 있도록 FK를 알려주어야 함 (DB 에서는 TEAM_ID를 FK로 지정하듯이)
    @JoinColumn(name="TEAM_ID")
    private Team team;

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    //값을 바꾸는 기능뿐만 아니라, 역방향 필드값도 바꿔주기 때문에 setTeam()이라는 이름은 적절하지 않음
    public void changeTeam(Team team) {
        this.team = team;
        this.getTeam().getMembers().add(this);  //연관관계 주인쪽에서 외래키 필드 값을 변경 -> 역방향 객체의 외래키 필드 값도 여기서 관리
    }
}
