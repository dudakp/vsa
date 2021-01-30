/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zapocet2;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "Predmet.byCode", query = "SELECT p FROM Predmet p WHERE p.kod = :kod"),
    @NamedQuery(name = "Predmet.count", query = "SELECT COUNT(p) FROM Predmet p JOIN p.profesor prof WHERE prof.meno = :meno")
})
public class Predmet implements Serializable {

    public Predmet() {}
    
    private static final long serialVersionUID = 1L;

    @Id
    private String kod;                 // primarny kluc - id predmetu
    private String rocnik; 
    
    @ManyToOne
    @JoinColumn(name = "OSOBA_ID", referencedColumnName = "ID")
    private Osoba profesor;             // osoba, ktora prednasa predmet

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getRocnik() {
        return rocnik;
    }

    public void setRocnik(String rocnik) {
        this.rocnik = rocnik;
    }

    public Osoba getProfesor() {
        return profesor;
    }

    public void setProfesor(Osoba profesor) {
        this.profesor = profesor;
    }

    

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kod != null ? kod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the kod fields are not set
        if (!(object instanceof Predmet)) {
            return false;
        }
        Predmet other = (Predmet) object;
        if ((this.kod == null && other.kod != null) || (this.kod != null && !this.kod.equals(other.kod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Predmet[ id=" + kod + " ]";
    }

}
