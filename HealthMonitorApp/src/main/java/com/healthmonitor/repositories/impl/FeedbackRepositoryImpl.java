package com.healthmonitor.repositories.impl;

import com.healthmonitor.pojo.Feedback;
import com.healthmonitor.repositories.FeedbackRepository;
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
public class FeedbackRepositoryImpl implements FeedbackRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Feedback> getFeedbackList(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Feedback> q = b.createQuery(Feedback.class);
        Root<Feedback> root = q.from(Feedback.class);
        q.orderBy(b.desc(root.get("createdAt")));
        q.select(root);

        if (params != null) {
        List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate codePredicate = b.like(root.get("code"), "%" + kw + "%");
                Predicate ratingPredicate = b.equal(root.get("rating"), "%" + kw + "%");
                Predicate commentPredicate = b.equal(root.get("comment"), "%" + kw + "%");
                predicates.add(b.or(codePredicate, ratingPredicate, commentPredicate));
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
    public Feedback createOrUpdateFeedback(Feedback pkg) {
        Session s = factory.getObject().getCurrentSession();

        if (pkg.getId() == null) {
            s.persist(pkg);
        } else {
            pkg = s.merge(pkg);
        }

        return pkg;
    }

    @Override
    public void deleteFeedback(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Feedback p = this.getFeedbackById(id);
        if (p != null) {
            s.remove(p);
        }
    }

    @Override
    public void deleteFeedbackList(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteFeedback(id);
        }
    }

    @Override
    public long countFeedbackList(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Feedback> root = q.from(Feedback.class);
        q.select(b.count(root));

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate codePredicate = b.like(root.get("code"), "%" + kw + "%");
                Predicate ratingPredicate = b.equal(root.get("rating"), "%" + kw + "%");
                Predicate commentPredicate = b.equal(root.get("comment"), "%" + kw + "%");
                predicates.add(b.or(codePredicate, ratingPredicate, commentPredicate));
            }

            if (!predicates.isEmpty()) {
                q.where(predicates.toArray(new Predicate[0]));
            }
        }

        return s.createQuery(q).getSingleResult();
    }

    @Override
    public Feedback getFeedbackById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Feedback.class, id);
    }

    @Override
    public String generateNextCode() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT MAX(s.id) FROM Feedback s", Integer.class);
        Integer maxId = (Integer) q.getSingleResult();

        int nextId = (maxId != null) ? maxId + 1 : 1;
        return "FBK" + String.format("%05d", nextId);
    }
    
}
