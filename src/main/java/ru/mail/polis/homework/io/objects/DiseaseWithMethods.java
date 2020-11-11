package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DiseaseWithMethods implements Serializable {
    enum DiseaseSeverity {
        LIGHT,
        MEDIUM,
        HIGH,
        SEVERE
    }

    private String diseaseName;
    private DiseaseSeverity severity;

    public DiseaseWithMethods(){

    }

    public DiseaseWithMethods(String diseaseName, DiseaseSeverity severity) {
        this.diseaseName = diseaseName;
        this.severity = severity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiseaseWithMethods disease = (DiseaseWithMethods) o;
        return diseaseName.equals(disease.diseaseName) &&
                severity == disease.severity;
    }

    private void writeObject(ObjectOutputStream obj) throws IOException {
        obj.writeUTF(diseaseName);
        obj.writeObject(severity);
    }

    private void readObject(ObjectInputStream obj) throws IOException, ClassNotFoundException {
        diseaseName = obj.readUTF();
        severity = (DiseaseSeverity) obj.readObject();
    }
}
