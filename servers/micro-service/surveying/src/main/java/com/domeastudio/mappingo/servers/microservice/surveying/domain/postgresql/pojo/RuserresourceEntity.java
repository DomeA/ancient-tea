package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "ruserresource", schema = "public", catalog = "postgres")
public class RuserresourceEntity {
    private String id;
    private TresourceEntity tresourceByReid;
    private TuserEntity tuserByUid;

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

        RuserresourceEntity that = (RuserresourceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (tresourceByReid != null ? !tresourceByReid.equals(that.tresourceByReid) : that.tresourceByReid != null)
            return false;
        if (tuserByUid != null ? !tuserByUid.equals(that.tuserByUid) : that.tuserByUid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tresourceByReid != null ? tresourceByReid.hashCode() : 0);
        result = 31 * result + (tuserByUid != null ? tuserByUid.hashCode() : 0);
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
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    public TuserEntity getTuserByUid() {
        return tuserByUid;
    }

    public void setTuserByUid(TuserEntity tuserByUid) {
        this.tuserByUid = tuserByUid;
    }
}
