import cn.dao.CustomerDao;
import cn.pojo.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JpqlTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void query() {
        Customer customer = customerDao.queryByCustAddress("beijing");
        System.out.println("customer = " + customer);
    }

    @Test
    @Transactional      //jpql删除或修改必须加事务
    @Rollback(value = false)    //jpql删除或修改事务会自动回滚
    public void update() {
        customerDao.updateCustLevelById(1L,"1");
    }

    @Test
    public void nativeQuery() {
        List<Object[]> sql = customerDao.findSql();
        sql.forEach(s->System.out.println(Arrays.toString(s)));
    }

    @Test
    public void likeQuery() {
        List<Customer> list = customerDao.findByCustNameLike("%tes%");
        list.forEach(System.out::println);
    }
}
