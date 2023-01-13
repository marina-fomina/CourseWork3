package ru.fomina.socks.model;

public enum Size {
    Size35 (35f),
    Size35_5 (35.5f),
    Size36 (36f),
    Size36_5 (36.5f),
    Size37 (37f),
    Size37_5 (37.5f),
    Size38 (38f),
    Size38_5 (38.5f),
    Size39 (39f),
    Size39_5 (39.5f),
    Size40 (40f),
    Size40_5 (40.5f),
    Size41 (41f),
    Size41_5 (41.5f),
    Size42 (42f),
    Size42_5 (42.5f);
    private final float size;

    Size(float size) {
        this.size = size;
    }
}
