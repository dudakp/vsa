package edu.iko.zadaniemv;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vsa
 */
@Entity
@Table(name = "PREDNASKA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prednaska.findAll", query = "SELECT p FROM Prednaska p"),
    @NamedQuery(name = "Prednaska.findById", query = "SELECT p FROM Prednaska p WHERE p.id = :id"),
    @NamedQuery(name = "Prednaska.findByName", query = "SELECT p FROM Prednaska p WHERE p.nazov = :nazov")
})
public class Prednaska implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nazov;

    @ElementCollection
    private List<String> autori = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "KONFERENCIA_ID", referencedColumnName = "ID")
    private Akcia konferencia;

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

    public List<String> getAutori() {
        return autori;
    }

    public void setAutori(List<String> autori) {
        this.autori = autori;
    }

    public Akcia getKonferencia() {
        return konferencia;
    }

    public void setKonferencia(Akcia konferencia) {
        this.konferencia = konferencia;
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
        if (!(object instanceof Prednaska)) {
            return false;
        }
        Prednaska other = (Prednaska) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cvicenie6.entities.Prednaska[ id=" + id + " ]";
    }

}
