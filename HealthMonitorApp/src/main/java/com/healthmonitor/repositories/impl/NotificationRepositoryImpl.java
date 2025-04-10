package com.healthmonitor.repositories.impl;

import com.healthmonitor.pojo.Notification;
import com.healthmonitor.repositories.NotificationRepository;
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
public class NotificationRepositoryImpl implements NotificationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<com.healthmonitor.pojo.Notification> getNotifications(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Notification> q = b.createQuery(Notification.class);
        Root<Notification> root = q.from(Notification.class);
        q.orderBy(b.desc(root.get("createdAt")));
        q.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate messagePredicate = b.like(root.get("message"), "%" + kw + "%");
                predicates.add(b.or(messagePredicate));
            }
        }

        if (!predicates.isEmpty()) {
            q.where(predicates.toArray(new Predicate[0]));
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
    public Notification createOrUpdateNotification(Notification pkg) {
        Session s = factory.getObject().getCurrentSession();

        if (pkg.getId() == null) {
            s.persist(pkg);
        } else {
            pkg = s.merge(pkg);
        }

        return pkg;
    }

    @Override
    public long countNotifications(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Notification> root = q.from(Notification.class);
        q.select(b.count(root));

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate messagePredicate = b.like(root.get("message"), "%" + kw + "%");
                predicates.add(b.or(messagePredicate));
            }
        }

        if (!predicates.isEmpty()) {
            q.where(predicates.toArray(new Predicate[0]));
        }

        return s.createQuery(q).getSingleResult();
    }

    @Override
    public Notification getNotificationById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Notification.class, id);
    }
}
