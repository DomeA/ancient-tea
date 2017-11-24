package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "rroleresource", schema = "public", catalog = "postgres")
public class RroleresourceEntity {
    private String id;
    private String reid;
    private String rid;
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

    @Basic
    @Column(name = "reid")
    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    @Basic
    @Column(name = "rid")
    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RroleresourceEntity that = (RroleresourceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (reid != null ? !reid.equals(that.reid) : that.reid != null) return false;
        if (rid != null ? !rid.equals(that.rid) : that.rid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (reid != null ? reid.hashCode() : 0);
        result = 31 * result + (rid != null ? rid.hashCode() : 0);
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
