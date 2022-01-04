package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id @GeneratedValue
    @Column(name="CATEGORY_ID")
    private Long id;

    private String name;

    //child와 parent 사이에 @ManyToOne, @OneToMany 어노테이션 어떻게 부여하는지 다시 생각해보기(헷갈려죽겠음)
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //아래의 다대다 관계를 중간테이블을 이용해 일대다, 다대일 관계로 풀어서 진행하는게 좋음
    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
        joinColumns = @JoinColumn(name = "CATEGORY_ID"),
        inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    private List<Item> items = new ArrayList<>();
}
