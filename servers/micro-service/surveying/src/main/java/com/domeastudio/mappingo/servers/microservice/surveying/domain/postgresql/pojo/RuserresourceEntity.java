package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "ruserresource", schema = "public", catalog = "postgres")
public class RuserresourceEntity {
    private String id;
    private String reid;
    private String uid;
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

    @Basic
    @Column(name = "reid")
    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuserresourceEntity that = (RuserresourceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (reid != null ? !reid.equals(that.reid) : that.reid != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (reid != null ? reid.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
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
