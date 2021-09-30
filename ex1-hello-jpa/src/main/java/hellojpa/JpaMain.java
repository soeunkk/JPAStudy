package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction1 = em.getTransaction();
        transaction1.begin();

        try {
            //비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            //영속
            System.out.println("=== BEFORE ===");
            em.persist(member); //쿼리문이 Before 과 After 사이에 출력 되지 않고 나중에 출력됨 -> 커밋 시점에 쿼리를 날림
            //em.detach(member);  //detach(): 영속성 컨테이너에서 엔티티를 지움
            System.out.println("=== AFTER ===");

            //1차 캐시 있는 엔티티 조회
            Member findMember = em.find(Member.class, 100L);    //위에서 엔티티를 만들 때 1차 캐시에 저장함 -> 1차 캐시에서 가져오므로 조회 쿼리를 날리지 않음

            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());

            //1차 캐시에 없는 엔티티 2번 조회
            Member findMember1 = em.find(Member.class, 1L);    //1차 캐시에 없는 1L에 대해 첫번째 조회를 할 때는 DB 에서 가져옴 (조회 쿼리를 날림)
            Member findMember2 = em.find(Member.class, 1L);    //두번째 조회부터는 1차 캐시에서 가져옴 (조회 쿼리를 날리지 않음)

            //영속성 엔티티 동일성 보장
            System.out.println(findMember1 == findMember2); //true

            //트랜잭션을 지원하는 쓰기 지연
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);
            System.out.println("=======================");  //이 출력 이후에 member1, member2에 대한 생성 쿼리를 날림

            member1.setName("C");   //1차 캐시에 있는 엔티티가 수정되면, 커밋 시, 스냅샷과 비교하여 자동으로 DB에 업데이트 쿼리를 날림

            //플러시
            Member member3 = new Member(200L, "member200");
            em.persist(member3);

            em.flush(); //플러시 매커니즘이 즉시 일어남
            System.out.println("======== FLUSH ========");  //이 출력 이전에 생성, 업데이트 쿼리를 날림

            transaction1.commit();  //커밋하는 이 시점에, 영속성 컨텍스트에 저장된 엔티티를 DB에 저장함 (쿼리를 돌림)
        } catch (Exception e) {
            transaction1.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
