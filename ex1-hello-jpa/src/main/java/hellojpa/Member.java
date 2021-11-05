package hellojpa;

import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {
    @Id //PK로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name")  //객체는 username 이라고 사용하지만 DB 에는 컬럼명 name 으로 저장
    private String username;

//    private Integer age;    //DB에 Integer 와 가장 적절한 숫자 타입으로 저장됨
//
//    @Enumerated(EnumType.STRING)    //자바에서 enum 타입으로 사용하는 필드를 DB에 저장
//    private RoleType roleType;
//
//    @Temporal(TemporalType.TIMESTAMP)   //TemporalType 에는 3가지가 있음 (TIMESTAMP:날짜+시간, DATE:날짜, TIME:시간)
//    private Date createdDate;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//
//    @Lob    //varchar 를 넘어서는 큰 컨텐츠 저장 (String 의 경우 CLOB 으로 저장됨)
//    private String description;
//
//    @Transient  //스프링 내부에서만 사용하는 필드 (필드에 아무런 어노테이션을 사용하지 않으면 DB에 있는 컬럼과 매핑되어야 함)
//    private int temp;

    public Member() {   //JPA 에서는 내부에서 동적으로 객체를 생성하기 때문에 기본 생성자가 필요함

    }

    public void setUsername(String username) {
        this.username = username;
    }
}
