package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;

    //한 팀 안에는 여러 멤버가 속할 수 있다고 가정
    //팀 입장에서는 자신이 1이고 멤버가 N인 관계임 (OneToMany)
    //무슨 객체와 연관되어 있는지 나타내기 위해 mappedBy에 상대 객체에서 Team 객체를 참조하고 있는 필드명을 써주어야 함 (현재는 Member 객체에서 team 이라는 필드명으로 Team 객체를 참조하고 있음)
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    //연관관계의 주인인 Member 에 changeTeam 을 작성하거나, Team 에 addMember 을 작성하여 사용 (둘 중 하나만!)
    /*
    public void addMember(Member member) {
        member.setTeam(this);   //연관관계의 주인인 Member 의 team 을 바꿔야 DB에 갱신되므로 꼭 써줘야 함
        members.add(member);    //객체지향을 위해 자신의 필드인 members 도 값을 갱신
    }
     */

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
