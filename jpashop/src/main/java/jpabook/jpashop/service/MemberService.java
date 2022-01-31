package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//데이터 변경은 꼭 트랜잭션이 있어야 함
//조회할 때 성능을 최적화시키기 위해 읽기에는 가급적 readOnly=true 넣자!
@Transactional(readOnly = true)
@RequiredArgsConstructor    //Construction Injection
public class MemberService {
    /*
    Injection
    1) Field Injection
        - 단점) 테스트코드 작성할 때 임의로 바꿀 수 없다.
    2) Setter Injection
        - 장점) 테스트코드 작성 시 원하는 것을 주입시킬 수가 있음
        - 단점) 개발자가 아닌 악의로 누군가가 바꿀 수 있음 (치명적!)
    3) Construction Injection
        - 특징) 생성자가 하나만 있을 때는 @Autowired 가 없어도 자동으로 주입해줌
        - 장점) Setter 의 장점은 유지하고, 단점은 보완함

    결론! 필드에 final 을 설정하고 @RequiredArgsConstructor 를 사용하자
     */

    //final 로 하면 주입을 했는지 컴파일 시점에서 체크할 수 있기 때문에 가급적 final 사용하자!
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional    //readOnly=true 를 없애기 위해 좁은 범위에 다시 넣음 (디폴트가 readOnly=false 이므로 명시할 필요 X)
    public Long join(Member member) {
        validateDuplicateMember(member);    //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    //중복 회원이면 예외를 발생시킴
    private void validateDuplicateMember(Member member) {
        //member name 필드에 유니크 제약조건을 거는 것이 제일 안전함!
        //여러 쓰레드가 이 메서드를 동시에 통과한다면 문제가 생길 수 있기 때문
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * ID로 회원 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
