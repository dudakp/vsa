/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dedicnost.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author vsa
 */
@Entity
public class Muzeum extends Budova implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Muzeum () {super();}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private float vstupne;
    
    private String otvorene;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getVstupne() {
        return vstupne;
    }

    public void setVstupne(float vstupne) {
        this.vstupne = vstupne;
    }

    public String getOtvorene() {
        return otvorene;
    }

    public void setOtvorene(String otvorene) {
        this.otvorene = otvorene;
    }   
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Muzeum)) {
            return false;
        }
        Muzeum other = (Muzeum) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dedicnost.entities.Muzeum[ id=" + id + " ]";
    }
    
}
