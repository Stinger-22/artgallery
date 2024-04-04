package com.artgallery.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="userId")
    @ToString.Exclude
    private User user;

    @ManyToMany
    @JoinTable(name = "image_tag",
            joinColumns = @JoinColumn(name = "tagId"),
            inverseJoinColumns = @JoinColumn(name = "imageId"))
    @ToString.Exclude
    private List<Tag> tags;

    @Column(length = 127)
    private String title;

    @Column(length = 2047)
    private String description;

    @Column(length = 63)
    private String filename;

    @Column(updatable = false)
    @CreatedDate
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    public Image(Long imageId, User user, String title, String description, Date creationDate) {
        this.imageId = imageId;
        this.user = user;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Image image = (Image) o;
        return getImageId() != null && Objects.equals(getImageId(), image.getImageId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
