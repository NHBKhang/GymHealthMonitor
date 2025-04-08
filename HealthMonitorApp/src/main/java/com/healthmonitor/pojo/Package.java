package com.healthmonitor.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "packages")
@NamedQueries({
    @NamedQuery(name = "Package.findAll", query = "SELECT p FROM Package p"),
    @NamedQuery(name = "Package.findById", query = "SELECT p FROM Package p WHERE p.id = :id"),
    @NamedQuery(name = "Package.findByName", query = "SELECT p FROM Package p WHERE p.name = :name"),
    @NamedQuery(name = "Package.findByDuration", query = "SELECT p FROM Package p WHERE p.duration = :id"),
    @NamedQuery(name = "Package.findByPrice", query = "SELECT p FROM Package p WHERE p.id = :id"),
    @NamedQuery(name = "Package.findByDescription", query = "SELECT p FROM Package p WHERE p.id = :id"),
    @NamedQuery(name = "Package.findByPtSessions", query = "SELECT p FROM Package p WHERE p.id = :id"),
    @NamedQuery(name = "Package.findByStatus", query = "SELECT p FROM Package p WHERE p.id = :id"),
    @NamedQuery(name = "Package.findByCreatedAt", query = "SELECT p FROM Package p WHERE p.id = :id")
})
public class Package implements Serializable {

    public enum PackageStatus {
        ACTIVE, INACTIVE
    }

    public enum PackageDuration {
        MONTHLY(1, "Gói tập tháng", 30),
        QUARTERLY(3, "Gói tập quý", 90),
        YEARLY(12, "Gói tập năm", 365);

        private final int durationInMonths;
        private final String description;
        private final int durationInDays;

        PackageDuration(int durationInMonths, String description, int durationInDays) {
            this.durationInMonths = durationInMonths;
            this.description = description;
            this.durationInDays = durationInDays;
        }

        public int getDurationInMonths() {
            return durationInMonths;
        }

        public int getDurationInDays() {
            return durationInDays;
        }

        public String getDescription() {
            return description;
        }

        public static PackageDuration fromMonths(int months) {
            for (PackageDuration pkg : values()) {
                if (pkg.durationInMonths == months) {
                    return pkg;
                }
            }
            return null;
        }
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "code", nullable = false, unique = true)
    @NotNull(message = "Mã gói không được để trống!")
    private String code;

    @Basic(optional = false)
    @Column(name = "name")
    @NotNull(message = "Tên gói không được để trống!")
    private String name;

    @Basic(optional = false)
    @Column(name = "duration")
    @Enumerated(EnumType.STRING)
    private PackageDuration duration;

    @Basic(optional = false)
    @Column(name = "price")
    private Double price;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "pt_sessions", nullable = true)
    private Integer ptSessions;

    @Basic(optional = false)
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private PackageStatus status;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private String createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gymPackage")
    private Set<Subscription> subscriptionSet;

    public Package() {
    }

    public Package(Integer id) {
        this.id = id;
    }

    public Package(Integer id, String code, String name, PackageDuration duration, Double price,
            String description, Integer ptSessions, PackageStatus status, String createdAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.description = description;
        this.ptSessions = ptSessions;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PackageDuration getDuration() {
        return duration;
    }

    public String getDurationName() {
        return duration.name();
    }

    public void setDuration(PackageDuration duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPtSessions() {
        return ptSessions;
    }

    public void setPtSessions(Integer ptSessions) {
        this.ptSessions = ptSessions;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public String getStatusName() {
        return status.name();
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Subscription> getSubscriptionSet() {
        return subscriptionSet;
    }

    public void setSubscriptionSet(Set<Subscription> subscriptionSet) {
        this.subscriptionSet = subscriptionSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Schedule)) {
            return false;
        }
        Package other = (Package) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.healthmonitor.pojo.Package[ code=" + code + " ]";
    }
}
