package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            //member.setTeamId(team.getId()); //team 의 Id를 찾아서 직접 설정해야 함 (객체지향스럽지 않음)
            member.setTeam(team);   //JPA 가 알아서 team 에서 PK 값을 꺼내 member 의 FK 값으로 설정해줌
            em.persist(member);

            //안해줘도 되지만 INSERT 쿼리문 다 나간 후에 SELECT 쿼리문 나가는걸 보고 싶을 때 (원래는 영속성컨텍스트에서 1차캐시로 바로 가져오므로 SELECT 쿼리문 실행 X)
            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            //Long findTeamId = findMember.getTeamId();
            //Team findTeam = em.find(Team.class, findTeamId);    //member 를 찾았음에도 team 을 찾으려면 다시 teamId로 찾아야 함 (객체지향스럽지 않음)
            Team findTeam = findMember.getTeam();   //member 에서 바로 team 객체를 끄집어 내서 사용할 수 있음
            System.out.println("findTeam = " + findTeam.getName());

            //member 를 통해 자신이 속한 팀의 멤버들을 출력
            List<Member> members = findMember.getTeam().getMembers();
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

            //연관관계 주인
            //team.getMembers().add(member);  //team 에 member 를 집어넣음 -> 하지만! 실제로 DB에 저장되지 않음. 왜? 연관관계 주인이 아닌, mappedBy인 쪽에 추가했으니까
            //그러나 위의 로직은 객체지향 측면에서 필요함 -> 해결책) 연관관계 주인에서 값을 바꿀 때 역방향의 값도 갱신하도록 메소드 변경
            member.changeTeam(team);           //연관관계 주인쪽에서 값을 변경했으므로 DB에 저장이 됨 + 역방향 값 갱신
            em.persist(team);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
