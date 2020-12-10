package cn.dao;

import cn.pojo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    //@Query("from Customer where custAddress = ?1")
    Customer queryByCustAddress(String address);

    @Query("update Customer set custLevel=?2 where custId=?1")
    @Modifying
    void updateCustLevelById(Long id, String level);

    @Query(value = "select * from cst_customer", nativeQuery = true)
    List<Object[]> findSql();

    List<Customer> findByCustNameLike(String name);
}
