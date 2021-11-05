package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Order order = em.find(Order.class, 1L);
            Long memberId = order.getMemberId();
            Member member = em.find(Member.class, memberId);    //Order 에서 memberId를 찾아 다시 Member 에서 조회한다는 거 자체가 매우 비효율적임 (객체지향적이지 않음->관계형 DB에 맞춰서 설계한 꼴)

            //Member findMember = order.getMember();  //이게 객체지향적. Order 에서 Member 객체로 바로 접근

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}