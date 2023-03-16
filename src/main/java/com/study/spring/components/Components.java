package com.study.spring.components;

public class Components {

    private FireBaseProperties fireBaseProperties;
    private Egoism egoism;

    public FireBaseProperties getFireBaseProperties() {
        return fireBaseProperties;
    }

    public Components setFireBaseProperties(FireBaseProperties fireBaseProperties) {
        this.fireBaseProperties = fireBaseProperties;
        return this;
    }

    public Egoism getEgoism() {
        return egoism;
    }

    public Components setEgoism(Egoism egoism) {
        this.egoism = egoism;
        return this;
    }
}
