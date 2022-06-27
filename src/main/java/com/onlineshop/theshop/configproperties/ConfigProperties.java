package com.onlineshop.theshop.configproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "theshop")
public class ConfigProperties {
    private Minio minio;
    private Typesense typesense;

    public Minio getMinio() {
        return minio;
    }

    public void setMinio(Minio minio) {
        this.minio = minio;
    }

    public Typesense getTypesense() {
        return typesense;
    }

    public void setTypesense(Typesense typesense) {
        this.typesense = typesense;
    }
}
