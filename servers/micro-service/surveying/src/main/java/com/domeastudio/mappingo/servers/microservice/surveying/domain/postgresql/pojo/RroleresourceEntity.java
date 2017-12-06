package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "rroleresource", schema = "public", catalog = "postgres")
public class RroleresourceEntity {
    private String id;
    private TresourceEntity tresourceByReid;
    private TroleEntity troleByRid;

    @Id
    @GeneratedValue(generator = "autoid")
    @GenericGenerator(name = "autoid", strategy = "uuid")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RroleresourceEntity that = (RroleresourceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (tresourceByReid != null ? !tresourceByReid.equals(that.tresourceByReid) : that.tresourceByReid != null)
            return false;
        if (troleByRid != null ? !troleByRid.equals(that.troleByRid) : that.troleByRid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tresourceByReid != null ? tresourceByReid.hashCode() : 0);
        result = 31 * result + (troleByRid != null ? troleByRid.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "reid", referencedColumnName = "reid")
    public TresourceEntity getTresourceByReid() {
        return tresourceByReid;
    }

    public void setTresourceByReid(TresourceEntity tresourceByReid) {
        this.tresourceByReid = tresourceByReid;
    }

    @ManyToOne
    @JoinColumn(name = "rid", referencedColumnName = "rid")
    public TroleEntity getTroleByRid() {
        return troleByRid;
    }

    public void setTroleByRid(TroleEntity troleByRid) {
        this.troleByRid = troleByRid;
    }
}
