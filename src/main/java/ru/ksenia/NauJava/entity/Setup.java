package ru.ksenia.NauJava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.ksenia.NauJava.dto.AmpConfig;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "setups")
public class Setup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guitar_id")
    private Guitar guitar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amp_id")
    private Amp amp;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", name = "amp_config")
    private AmpConfig ampConfig;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cab_id")
    private Cab cab;

    @OneToMany(mappedBy = "setup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedalSlot> pedalboard = new ArrayList<>();

    public Setup(String name, String description, User user, Guitar guitar, Amp amp, AmpConfig ampConfig, Cab cab) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.guitar = guitar;
        this.amp = amp;
        this.ampConfig = ampConfig;
        this.cab = cab;
    }
}
