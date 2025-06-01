package com.github.alexandersv91.bardsey.urlshortenerservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "Url")
@Table(
        name = "urls",
        indexes = {
                @Index(
                        name = "idx_url_short_url_unq",
                        columnList = "short_url"
                )
        }
)
public class Url implements Serializable {

    @Serial
    private static final long serialVersionUID = 1172540243206383183L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_seq")
    @SequenceGenerator(name = "url_seq")
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "original_url", nullable = false)
    String originalUrl;

    @Column(name = "short_url", unique = true, nullable = false)
    String shortUrl;

    @CreatedDate
    @Column(name = "created_date")
    Instant creationDate;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o)
                .getHibernateLazyInitializer()
                .getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer()
                .getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Url url = (Url) o;
        return getId() != null && Objects.equals(getId(), url.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer()
                .getPersistentClass()
                .hashCode() : getClass().hashCode();
    }
}
