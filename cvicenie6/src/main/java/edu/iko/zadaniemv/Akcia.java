package edu.iko.zadaniemv;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vsa
 */
@Entity
@Table(name = "AKCIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Akcia.findAll", query = "SELECT a FROM Akcia a"),
    @NamedQuery(name = "Akcia.findById", query = "SELECT a FROM Akcia a WHERE a.id = :id"),
    @NamedQuery(name = "Akcia.findByName", query = "SELECT a FROM Akcia a WHERE a.nazov = :nazov")
})
public class Akcia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nazov;

    @Temporal(TemporalType.DATE)
    private Date termin;

    @OneToMany(mappedBy = "konferencia", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Prednaska> prednasky;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public Date getTermin() {
        return termin;
    }

    public void setTermin(Date termin) {
        this.termin = termin;
    }

    public List<Prednaska> getPrednasky() {
        return prednasky;
    }

    public void setPrednasky(List<Prednaska> prednasky) {
        this.prednasky = prednasky;
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
        if (!(object instanceof Akcia)) {
            return false;
        }
        Akcia other = (Akcia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Akcia{" + "id=" + id + ", nazov=" + nazov + ", termin=" + termin + ", prednasky=" + prednasky + '}';
    }

}
