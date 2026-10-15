package ru.ksenia.NauJava.enums;

public enum PedalTypes {
    TUNER(10, ChainZone.PRE_AMP),
    OCTAVER(12, ChainZone.PRE_AMP),
    PITCH_SHIFTER(14, ChainZone.PRE_AMP),
    HARMONIZER(16, ChainZone.PRE_AMP),
    
    WAH(18, ChainZone.PRE_AMP),
    AUTO_WAH(19, ChainZone.PRE_AMP),
    TALK_BOX(20, ChainZone.PRE_AMP),
    COMPRESSOR(22, ChainZone.PRE_AMP),
    SUSTAIN(24, ChainZone.PRE_AMP),

    BOOST(25, ChainZone.PRE_AMP),
    OVERDRIVE(26, ChainZone.PRE_AMP),
    DISTORTION(28, ChainZone.PRE_AMP),
    FUZZ(30, ChainZone.PRE_AMP),

    NOISE_GATE(32, ChainZone.PRE_AMP),

    VOLUME(34, ChainZone.PRE_AMP),
    EQ(36, ChainZone.PRE_AMP),
    
    PHASER(38, ChainZone.FX_LOOP),
    FLANGER(40, ChainZone.FX_LOOP),
    CHORUS(42, ChainZone.FX_LOOP),
    TREMOLO(44, ChainZone.FX_LOOP),

    DELAY(46, ChainZone.FX_LOOP),
    REVERB(48, ChainZone.FX_LOOP),
    
    LOOPER(50, ChainZone.FX_LOOP);

    private final int weight;
    private final ChainZone chainZone;
    PedalTypes(int weight, ChainZone chainZone){
        this.weight = weight;
        this.chainZone = chainZone;
    }

    public int getWeight() {
        return weight;
    }
    public ChainZone getChainZone() {
        return chainZone;
    }
}
