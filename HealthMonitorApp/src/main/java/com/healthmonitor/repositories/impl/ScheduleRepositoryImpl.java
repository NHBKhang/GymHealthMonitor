package com.healthmonitor.repositories.impl;

import com.healthmonitor.pojo.Schedule;
import com.healthmonitor.repositories.ScheduleRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Schedule> getSchedules(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Schedule> q = b.createQuery(Schedule.class);
        Root<Schedule> root = q.from(Schedule.class);
        q.orderBy(b.desc(root.get("createdAt")));
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate namePredicate = b.like(root.get("name"), "%" + kw + "%");
                Predicate sessionsPredicate = b.like(root.get("ptSessions"), "%" + kw + "%");
                Predicate pricePredicate = b.like(root.get("price"), "%" + kw + "%");
                Predicate durationPredicate = b.like(root.get("duration"), "%" + kw + "%");
                predicates.add(b.or(namePredicate, sessionsPredicate, pricePredicate, durationPredicate));
            }
            if (!predicates.isEmpty()) {
                q.where(predicates.toArray(new Predicate[0]));
            }
        }

        Query query = s.createQuery(q);

        if (params != null) {
            int page = 1;
            try {
                page = Integer.parseInt(params.getOrDefault("page", "1"));
            } catch (NumberFormatException e) {
                page = 1;
            }
            int start = (page - 1) * PAGE_SIZE;
            query.setFirstResult(start);
            query.setMaxResults(PAGE_SIZE);
        }

        return query.getResultList();
    }

    @Override
    public Schedule createOrUpdateSchedule(Schedule schedule) {
        Session s = factory.getObject().getCurrentSession();

        if (schedule.getId() == null) {
            s.persist(schedule);
        } else {
            schedule = s.merge(schedule);
        }

        return schedule;
    }

    @Override
    public void deleteSchedule(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Schedule scd = this.getScheduleById(id);
        if (scd != null) {
            s.remove(scd);
        }
    }

    @Override
    public void deleteSchedules(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteSchedule(id);
        }
    }

    @Override
    public long countSchedules(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Schedule> root = q.from(Schedule.class);
        q.select(b.count(root));

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate namePredicate = b.like(root.get("name"), "%" + kw + "%");
                Predicate sessionsPredicate = b.like(root.get("ptSessions"), "%" + kw + "%");
                Predicate pricePredicate = b.like(root.get("price"), "%" + kw + "%");
                Predicate durationPredicate = b.like(root.get("duration"), "%" + kw + "%");
                predicates.add(b.or(namePredicate, sessionsPredicate, pricePredicate, durationPredicate));
            }

            if (!predicates.isEmpty()) {
                q.where(predicates.toArray(new Predicate[0]));
            }
        }

        return s.createQuery(q).getSingleResult();
    }

    @Override
    public Schedule getScheduleById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Schedule.class, id);
    }

    @Override
    public String generateNextCode() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT MAX(s.id) FROM Schedule s", Integer.class);
        Integer maxId = (Integer) q.getSingleResult();

        int nextId = (maxId != null) ? maxId + 1 : 1;
        return "SCD" + String.format("%05d", nextId);
    }

    public static final int getPageSize() {
        return ScheduleRepositoryImpl.PAGE_SIZE;
    }

}
