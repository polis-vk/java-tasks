package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class DiseaseExternalizable implements Externalizable {
     enum DiseaseSeverity {
        LIGHT,
        MEDIUM,
        HIGH,
        SEVERE
    }

    private String diseaseName;
    private DiseaseSeverity severity;

    public DiseaseExternalizable(){

    }

    public DiseaseExternalizable(String diseaseName, DiseaseSeverity severity) {
        this.diseaseName = diseaseName;
        this.severity = severity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiseaseExternalizable disease = (DiseaseExternalizable) o;
        return diseaseName.equals(disease.diseaseName) &&
                severity == disease.severity;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(diseaseName);
        out.writeObject(severity);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        diseaseName = in.readUTF();
        severity = (DiseaseSeverity) in.readObject();
    }
}
