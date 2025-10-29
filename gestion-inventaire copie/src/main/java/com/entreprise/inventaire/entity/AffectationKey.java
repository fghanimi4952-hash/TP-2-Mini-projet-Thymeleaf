package com.entreprise.inventaire.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AffectationKey implements Serializable {
    private Long materielId;
    private Long employeId;
    
    public AffectationKey() {}
    
    public AffectationKey(Long materielId, Long employeId) {
        this.materielId = materielId;
        this.employeId = employeId;
    }
    
    public Long getMaterielId() { return materielId; }
    public void setMaterielId(Long materielId) { this.materielId = materielId; }
    
    public Long getEmployeId() { return employeId; }
    public void setEmployeId(Long employeId) { this.employeId = employeId; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AffectationKey that = (AffectationKey) o;
        return Objects.equals(materielId, that.materielId) && 
               Objects.equals(employeId, that.employeId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(materielId, employeId);
    }
}
