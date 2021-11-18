package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    //양방향 관계를 만드는 것은 그리 좋은 방법은 아님 (이 부분은 코드 짜는 방법을 보여주려고 넣은 것) -> 관심사를 끊어내는 것이 중요!
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>(); //관례상 초기값을 ArrayList 로 설정 (NullPointException 방지)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
