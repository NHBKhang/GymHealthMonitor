package com.healthmonitor.repositories.impl;

import com.healthmonitor.pojo.Member;
import com.healthmonitor.pojo.Trainer;
import com.healthmonitor.pojo.User;
import com.healthmonitor.pojo.User.Role;
import com.healthmonitor.repositories.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<User> getUsers(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root<User> root = q.from(User.class);
        q.orderBy(b.desc(root.get("createdAt")));
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate fullNamePredicate = b.like(
                        b.concat(root.get("firstName"), b.concat(" ", root.get("lastName"))),
                        "%" + kw + "%"
                );
                Predicate usernamePredicate = b.like(root.get("username"), "%" + kw + "%");
                Predicate emailPredicate = b.like(root.get("email"), "%" + kw + "%");
                Predicate phonePredicate = b.like(root.get("phone"), "%" + kw + "%");
                predicates.add(b.or(fullNamePredicate, usernamePredicate, emailPredicate, phonePredicate));
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
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(User.class, id);
    }

    @Override
    public Member getMemberByUserId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Member t WHERE t.user.id = :id", Member.class);
        q.setParameter("id", id);
        return (Member) q.getSingleResult();
    }

    @Override
    public Trainer getTrainerByUserId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Trainer t WHERE t.user.id = :id", Trainer.class);
        q.setParameter("id", id);
        return (Trainer) q.getSingleResult();
    }

    @Override
    public User createOrUpdateUser(User user) {
        Session s = factory.getObject().getCurrentSession();

        if (user.getId() == null) {
            if (user.getRole() == Role.MEMBER) {
                Member member = new Member(user);
                if (user.getMember() != null) {
                    member.setHeight(user.getMember().getHeight());
                    member.setWeight(user.getMember().getWeight());
                    member.setFitnessGoal(user.getMember().getFitnessGoal());
                }
                user.setMember(member);
                s.persist(user);
                s.persist(member);
            } else if (user.getRole() == Role.TRAINER) {
                Trainer trainer = new Trainer(user);
                if (user.getTrainer() != null) {
                    trainer.setMajor(user.getTrainer().getMajor());
                }
                user.setTrainer(trainer);
                s.persist(user);
                s.persist(trainer);
            } else {
                s.persist(user);
            }
        } else {
            User existingUser = s.get(User.class, user.getId());
            BeanUtils.copyProperties(user, existingUser, "id", "member", "trainer");

            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            }

            if (user.getRole() == Role.MEMBER) {
                if (existingUser.getMember() == null) {
                    existingUser.setMember(new Member(existingUser));
                }
                existingUser.getMember().setHeight(user.getMember().getHeight());
                existingUser.getMember().setWeight(user.getMember().getWeight());
                existingUser.getMember().setFitnessGoal(user.getMember().getFitnessGoal());
                existingUser.setTrainer(null);
            } else if (user.getRole() == Role.TRAINER) {
                if (existingUser.getTrainer() == null) {
                    existingUser.setTrainer(new Trainer(existingUser));
                }
                existingUser.getTrainer().setMajor(user.getTrainer().getMajor());
                existingUser.setMember(null);
            } else {
                existingUser.setTrainer(null);
                existingUser.setMember(null);
            }

            user = (User) s.merge(existingUser);
        }

        return user;
    }

    @Override
    public void deleteUser(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        User user = this.getUserById(id);
        if (user != null) {
            s.remove(user);
        }
    }

    @Override
    public long countUsers(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<User> root = q.from(User.class);
        q.select(b.count(root));

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate fullNamePredicate = b.like(
                        b.concat(root.get("firstName"), b.concat(" ", root.get("lastName"))),
                        "%" + kw + "%"
                );
                Predicate usernamePredicate = b.like(root.get("username"), "%" + kw + "%");
                Predicate emailPredicate = b.like(root.get("email"), "%" + kw + "%");
                Predicate phonePredicate = b.like(root.get("phone"), "%" + kw + "%");
                predicates.add(b.or(fullNamePredicate, usernamePredicate, emailPredicate, phonePredicate));
            }

            if (!predicates.isEmpty()) {
                q.where(predicates.toArray(new Predicate[0]));
            }
        }

        return s.createQuery(q).getSingleResult();
    }

    public static final int getPageSize() {
        return UserRepositoryImpl.PAGE_SIZE;
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query q = s.createNamedQuery("User.findByUsername", User.class);
            q.setParameter("username", username);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
