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
            Member member = new Member();
            member.setUsername("member1");   //참고로 실무에서는 세터함수 잘 안씀 -> 빌더 이용

            em.persist(member);

            Team team = new Team();
            team.setName("teamA");
            team.getMembers().add(member);  //팀의 멤버로 member 추가

            //team을 손댔는데 MEMBER 테이블이 업데이트 되는 것을 보고 헷갈릴 수 있음 -> 이런 식의 테이블이 많아지면 운영이 힘들어짐
            em.persist(team);   //DB의 TEAM 테이블에 team이 추가됨과 함께 MEMBER 테이블에 있는 member의 TEAM_ID를 변경하기 위해 업데이트 쿼리가 나간다.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
