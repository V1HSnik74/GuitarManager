package ru.ksenia.NauJava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "guitars")
public class Guitar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, name = "strings_count", columnDefinition = "INT DEFAULT 6")
    private int stringsCount = 6;

    @Column(nullable = false, name = "pickup_type")
    private String pickupType;

    @Column(nullable = false, name="guitar_type")
    private String guitarType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "guitar")
    private List<Setup> setups = new ArrayList<>();

    public Guitar(String brand, String model, int stringsCount, String pickupType, String guitarType, String description, User user) {
        this.brand = brand;
        this.model = model;
        this.stringsCount = stringsCount;
        this.pickupType = pickupType;
        this.guitarType = guitarType;
        this.description = description;
        this.user = user;
    }
}
