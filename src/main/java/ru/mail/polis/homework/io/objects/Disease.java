package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Disease implements Serializable {
    enum DiseaseSeverity {
        LIGHT,
        MEDIUM,
        HIGH,
        SEVERE
    }

    private String diseaseName;
    private DiseaseSeverity severity;

    public Disease(){}

    public Disease(String diseaseName, DiseaseSeverity severity) {
        this.diseaseName = diseaseName;
        this.severity = severity;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public DiseaseSeverity getSeverity() {
        return severity;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public void setSeverity(DiseaseSeverity severity) {
        this.severity = severity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disease disease = (Disease) o;
        return diseaseName.equals(disease.diseaseName) &&
                severity == disease.severity;
    }
}
