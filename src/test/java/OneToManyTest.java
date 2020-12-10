import cn.dao.CustomerDao;
import cn.dao.LinkManDao;
import cn.pojo.Customer;
import cn.pojo.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToManyTest {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    @Test
    @Transactional
    @Rollback(value = false)
    public void add() {
        Customer customer = new Customer();
        customer.setCustName("test2");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("test2");
        linkMan.setCustomer(customer);      //方法二 发送两条insert
        customerDao.save(customer);
        linkManDao.save(linkMan);
        //customer.getLinkMans().add(linkMan); //方法一 发送两条insert和一条update 所以一的一方不设置
    }

    //对象导航查询
    @Test
    @Transactional
    public void query() {
        Customer customer = customerDao.getOne(7L);
        System.out.println("customer = " + customer);
    }

    @Test
    @Transactional
    public void query1() {
        Optional<Customer> byId = customerDao.findById(6L);//对象导航查询 默认延时加载 fetch可设置
        byId.ifPresent(System.out::println);

    }

    //TODO 级联删除

}
