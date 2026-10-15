package ru.ksenia.NauJava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ksenia.NauJava.enums.AmpTypes;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "amps")
public class Amp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "amp_type")
    private AmpTypes ampType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "amp")
    private List<Setup> setups = new ArrayList<>();

    public Amp(String brand, String model, AmpTypes ampType, String description, User user) {
        this.brand = brand;
        this.model = model;
        this.ampType = ampType;
        this.description = description;
        this.user = user;
    }
}
