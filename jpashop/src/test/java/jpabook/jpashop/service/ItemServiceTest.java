package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Fail.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 아이템_등록() throws Exception {
        //given
        Item item = new Book();
        item.setName("곰세마리");

        //when
        Long savedId = itemService.saveItem(item);

        //then
        Assertions.assertThat(itemRepository.findOne(savedId)).isEqualTo(item);
    }

    @Test
    public void 수량_증가() throws Exception {
        //given
        Item item = new Book();
        item.setName("곰세마리");
        item.setStockQuantity(0);

        //when
        item.addStock(3);

        //then
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(3);
    }

    @Test(expected = NotEnoughStockException.class)
    public void 수량_감소_예외() throws Exception {
        //given
        Item item = new Book();
        item.setName("곰세마리");
        item.setStockQuantity(0);

        //when
        item.removeStock(1);    //예외 발생해야 함

        //then
        fail("예외가 발생해야 한다.");
    }
}