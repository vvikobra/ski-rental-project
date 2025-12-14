package vvikobra.miit.skirental.models.enums;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import vvikobra.miit.skirental.models.entities.BaseEntity;
import vvikobra.miit.skirental.models.entities.Skipass;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skipass_types")
public class SkipassType extends BaseEntity {
    private String name;
    private Set<Skipass> skipasses;

    public SkipassType(String name) {
        this.name = name;
        this.skipasses = new HashSet<>();
    }

    protected SkipassType() {
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "type", targetEntity = Skipass.class)
    public Set<Skipass> getSkipasses() {
        return skipasses;
    }

    public void setSkipasses(Set<Skipass> skipasses) {
        this.skipasses = skipasses;
    }
}
