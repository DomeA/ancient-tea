package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "trole", schema = "public", catalog = "postgres")
public class TroleEntity {
    private String rid;
    private String name;
    private String describe;
    private Set<RroleresourceEntity> rroleresourcesByRid;
    private Set<RuserroleEntity> ruserrolesByRid;

    @Id
    @GeneratedValue(generator = "autoid")
    @GenericGenerator(name = "autoid", strategy = "uuid")
    @Column(name = "rid")
    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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

        TroleEntity that = (TroleEntity) o;

        if (rid != null ? !rid.equals(that.rid) : that.rid != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (describe != null ? !describe.equals(that.describe) : that.describe != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rid != null ? rid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (describe != null ? describe.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "troleByRid")
    public Set<RroleresourceEntity> getRroleresourcesByRid() {
        return rroleresourcesByRid;
    }

    public void setRroleresourcesByRid(Set<RroleresourceEntity> rroleresourcesByRid) {
        this.rroleresourcesByRid = rroleresourcesByRid;
    }

    @OneToMany(mappedBy = "troleByRid")
    public Set<RuserroleEntity> getRuserrolesByRid() {
        return ruserrolesByRid;
    }

    public void setRuserrolesByRid(Set<RuserroleEntity> ruserrolesByRid) {
        this.ruserrolesByRid = ruserrolesByRid;
    }

    @Basic
    @Column(name = "describe")
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
