import cn.dao.CustomerDao;
import cn.pojo.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JpaTest1 {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void query() {
        Optional<Customer> customer = customerDao.findById(6L);
//        customer.ifPresent(System.out::println);
        customer.ifPresent(e->{
            System.out.println(e);
            System.out.println(e.getLinkMans());
        });
    }

    @Test
    public void saveOrUpdate() {
        //没有主键是保存操作     有主键是更新操作
        Customer customer = new Customer();
        customer.setCustName("test3");
        Customer save = customerDao.save(customer);
        System.out.println("save = " + save);
    }

    @Test
    public void delete() {
        customerDao.deleteById(24L);
    }

    @Test
    public void count() {
        long count = customerDao.count();
        System.out.println("count = " + count);
    }

    @Test
    public void exist() {
        boolean b = customerDao.existsById(2L);
        System.out.println("b = " + b);
    }

    @Test
    @Transactional
    public void get() {
        Customer customer = customerDao.getOne(22L);     //延时加载
        System.out.println("customer = " + customer);
    }


}
