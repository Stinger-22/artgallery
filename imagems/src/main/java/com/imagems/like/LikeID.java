package com.imagems.like;

import com.imagems.image.Image;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class LikeID implements Serializable {
    @ManyToOne
    @JoinColumn(name = "imageId")
    private Image imageId;

    private Long userId;

    public LikeID(Image imageId, Long userId) {
        this.imageId = imageId;
        this.userId = userId;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LikeID likeID = (LikeID) o;
        return imageId != null && Objects.equals(imageId, likeID.imageId)
                && userId != null && Objects.equals(userId, likeID.userId);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(imageId, userId);
    }
}
