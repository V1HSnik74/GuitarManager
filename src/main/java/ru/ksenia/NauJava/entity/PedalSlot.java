package ru.ksenia.NauJava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ksenia.NauJava.enums.ChainZone;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="pedal_slots")
public class PedalSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setup_id", nullable = false)
    private Setup setup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedal_id", nullable = false)
    private Pedal pedal;

    @Column(nullable = false, name="order_index")
    private int orderIndex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name="chain_zone")
    private ChainZone chainZone;

    @Column(columnDefinition = "TEXT")
    private String config;

    public PedalSlot(Setup setup, Pedal pedal, int orderIndex, ChainZone chainZone, String config) {
        this.setup = setup;
        this.pedal = pedal;
        this.orderIndex = orderIndex;
        this.chainZone = chainZone;
        this.config = config;
    }
}
