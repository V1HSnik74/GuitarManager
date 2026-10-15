package ru.ksenia.NauJava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ksenia.NauJava.enums.PedalTypes;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pedals")
public class Pedal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "pedal_type")
    private PedalTypes pedalType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "pedal")
    private List<PedalSlot> pedalboard = new ArrayList<>();

    public Pedal(String brand, String model, PedalTypes pedalType, String description, User user) {
        this.brand = brand;
        this.model = model;
        this.pedalType = pedalType;
        this.description = description;
        this.user = user;
    }
}
