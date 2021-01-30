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
public class Nemocnica extends Budova implements Serializable {

    private static final long serialVersionUID = 1L;

    public Nemocnica() {
        super();
    }
    
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Boolean pohotovost;
    
    private Boolean lekaren;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPohotovost() {
        return pohotovost;
    }

    public void setPohotovost(Boolean pohotovost) {
        this.pohotovost = pohotovost;
    }

    public Boolean getLekaren() {
        return lekaren;
    }

    public void setLekaren(Boolean lekaren) {
        this.lekaren = lekaren;
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
        if (!(object instanceof Nemocnica)) {
            return false;
        }
        Nemocnica other = (Nemocnica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dedicnost.entities.Nemocnica[ id=" + id + " ]";
    }
    
}
