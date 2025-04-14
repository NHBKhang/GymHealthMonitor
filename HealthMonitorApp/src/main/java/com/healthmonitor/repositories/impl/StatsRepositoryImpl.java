package com.healthmonitor.repositories.impl;

import com.healthmonitor.repositories.StatsRepository;
import jakarta.data.repository.Param;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {
    
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> getUserStats(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate) {
        Session s = this.factory.getObject().getCurrentSession();

        Query q = s.createQuery(
                "SELECT DATE(u.createdAt), COUNT(u) "
                + "FROM User u "
                + "WHERE u.createdAt BETWEEN :fromDate AND :toDate "
                + "GROUP BY DATE(u.createdAt)",
                Object[].class
        );

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59);

        q.setParameter("fromDate", fromDateTime);
        q.setParameter("toDate", toDateTime);

        return q.getResultList();
    }

    @Override
    public List<Object[]> getRevenueStats(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate) {
        Session s = this.factory.getObject().getCurrentSession();

        Query q = s.createQuery(
                "SELECT DATE(p.createdAt), SUM(p.amount) "
                + "FROM Payment p "
                + "WHERE p.createdAt BETWEEN :fromDate AND :toDate and p.status = 'SUCCESS'"
                + "GROUP BY DATE(p.createdAt)",
                Object[].class
        );

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59);

        q.setParameter("fromDate", fromDateTime);
        q.setParameter("toDate", toDateTime);

        return q.getResultList();
    }
    
}
