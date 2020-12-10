import cn.dao.CustomerDao;
import cn.pojo.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpecTest1 {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void fun() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");
                Predicate predicate = criteriaBuilder.equal(custName, "test2");
                return predicate;
            }
        };
        Optional<Customer> customer = customerDao.findOne(spec);
        customer.ifPresent(System.out::println);
    }

    @Test
    public void fun2() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");
                Path<Object> custAddress = root.get("custAddress");
                Predicate p1 = criteriaBuilder.equal(custAddress, "beijing");
                Predicate p2 = criteriaBuilder.equal(custName, "test1");
                Predicate predicate = criteriaBuilder.and(p1, p2);
                return predicate;
            }
        };
        Optional<Customer> one = customerDao.findOne(spec);
        one.ifPresent(System.out::println);
    }

    @Test
    public void fun3() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");
                Predicate like = criteriaBuilder.like(custName.as(String.class), "%test%");
                return like;
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "custId");
        List<Customer> all = customerDao.findAll(spec,sort);
        all.forEach(System.out::println);
    }

    @Test
    public void fun4(){
        Pageable pageable = new PageRequest(0, 1);
        Page<Customer> all = customerDao.findAll(pageable);
        System.out.println(all.getTotalElements());
        System.out.println(all.getTotalPages());
        all.getContent().forEach(System.out::println);
    }
}
