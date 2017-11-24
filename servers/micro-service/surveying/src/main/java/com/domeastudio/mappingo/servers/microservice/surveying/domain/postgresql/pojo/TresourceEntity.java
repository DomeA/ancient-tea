package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "tresource", schema = "public", catalog = "postgres")
public class TresourceEntity {
    private String reid;
    private String name;
    private Set<RroleresourceEntity> rroleresourcesByReid;
    private Set<RuserresourceEntity> ruserresourcesByReid;

    @Id
    @GeneratedValue(generator = "autoid")
    @GenericGenerator(name = "autoid", strategy = "uuid")
    @Column(name = "reid")
    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TresourceEntity that = (TresourceEntity) o;

        if (reid != null ? !reid.equals(that.reid) : that.reid != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reid != null ? reid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "tresourceByReid")
    public Set<RroleresourceEntity> getRroleresourcesByReid() {
        return rroleresourcesByReid;
    }

    public void setRroleresourcesByReid(Set<RroleresourceEntity> rroleresourcesByReid) {
        this.rroleresourcesByReid = rroleresourcesByReid;
    }

    @OneToMany(mappedBy = "tresourceByReid")
    public Set<RuserresourceEntity> getRuserresourcesByReid() {
        return ruserresourcesByReid;
    }

    public void setRuserresourcesByReid(Set<RuserresourceEntity> ruserresourcesByReid) {
        this.ruserresourcesByReid = ruserresourcesByReid;
    }
}
