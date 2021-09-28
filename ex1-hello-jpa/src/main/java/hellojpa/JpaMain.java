package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //createEntityManagerFactory() 파라미터로 persistence.xml 에서 작성한 유닛 이름을 넣어주어야 함
        //따라서 사용하는 데이터베이스가 여러 개라면 유닛도 여러 개이므로 emf 도 여러 개 만들어야 함
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();   //데이터베이스에 쿼리를 날릴 때마다 하나의 EntityManager 를 만들어야 함 (쓰레드간 공유 XXX)

        EntityTransaction transaction1 = em.getTransaction();  //JPA 에서 데이터베이스를 변경하는 모든 작업은 꼭 트랜잭션 안에서 작업 해야 함
        transaction1.begin();    //트랜잭션 시작

        try {
            Member member = new Member();
            member.setId(2L);
            member.setName("soeun2");

            /* persist(): 데이터베이스에 데이터를 저장 */
            em.persist(member);

            /* find([테이블 타입], [PK]): 데이터베이스에서 PK로 데이터 조회 */
            Member findMember = em.find(Member.class, 1L);

            /* createQuery(): 직접 쿼리를 통해 데이터를 CRUD */
            // JPQL: 쿼리를 짤 때도 객체 관점으로 짬 (select "m")
            // setFirstResult([idx]): 몇번째 row 부터 가져올 지에 대한 인덱스
            // setMaxResults([idx]): 최대 가져올 row 개수
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();

            for (Member m : result) {System.out.println("member.getName() = " + m.getName());}

            /* 데이터의 수정!!!: 수정은 별다른 jpa 메서드를 사용하지 않아도 값이 변경되면 "자동으로" DB 값도 변경해준다. */
            findMember.setName("soeunKim");

            /* remove(): 데이터베이스에서 데이터 삭제 */
            //em.remove(findMember);

            transaction1.commit();   //commit(): 트랜잭션 종료
        } catch (Exception e) {
            transaction1.rollback(); //rollback(): 트랜잭션 취소
        } finally {
            em.close();
        }

        emf.close();
    }
}
