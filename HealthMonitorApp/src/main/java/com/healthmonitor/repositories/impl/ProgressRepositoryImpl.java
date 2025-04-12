package com.healthmonitor.repositories.impl;

import com.healthmonitor.pojo.Progress;
import com.healthmonitor.repositories.ProgressRepository;
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
public class ProgressRepositoryImpl implements ProgressRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Progress> getProgress(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Progress> q = b.createQuery(Progress.class);
        Root<Progress> root = q.from(Progress.class);
        q.orderBy(b.desc(root.get("date")));
        q.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate codePredicate = b.like(root.get("code"), "%" + kw + "%");
                predicates.add(b.or(codePredicate));
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
    public Progress createOrUpdateProgress(Progress progress) {
        Session s = factory.getObject().getCurrentSession();

        if (progress.getId() == null) {
            s.persist(progress);
        } else {
            progress = s.merge(progress);
        }

        return progress;
    }

    @Override
    public void deleteProgress(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Progress p = this.getProgressById(id);
        if (p != null) {
            s.remove(p);
        }

    }

    @Override
    public void deleteProgressList(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteProgress(id);
        }
    }

    @Override
    public long countProgress(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Progress> root = q.from(Progress.class);
        q.select(b.count(root));

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate codePredicate = b.like(root.get("code"), "%" + kw + "%");
                predicates.add(b.or(codePredicate));
            }
        }

        if (!predicates.isEmpty()) {
            q.where(predicates.toArray(new Predicate[0]));
        }

        return s.createQuery(q).getSingleResult();
    }

    @Override
    public Progress getProgressById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Progress.class, id);
    }

    @Override
    public String generateNextCode() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT MAX(p.id) FROM Progress p", Integer.class);
        Integer maxId = (Integer) q.getSingleResult();

        int nextId = (maxId != null) ? maxId + 1 : 1;
        return "PGS" + String.format("%05d", nextId);
    }
}
