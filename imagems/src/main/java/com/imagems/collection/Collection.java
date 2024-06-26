package com.imagems.collection;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.imagems.image.Image;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectionId;

    @Column(length = 31)
    private String title;

    private Long userId;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "collection_image",
            joinColumns = @JoinColumn(name = "imageId"),
            inverseJoinColumns = @JoinColumn(name = "collectionId"))
    @ToString.Exclude
    private List<Image> images;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Collection that = (Collection) o;
        return getCollectionId() != null && Objects.equals(getCollectionId(), that.getCollectionId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
