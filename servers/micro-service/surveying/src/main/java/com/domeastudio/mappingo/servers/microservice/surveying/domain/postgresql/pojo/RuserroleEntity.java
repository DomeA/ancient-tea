package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "ruserrole", schema = "public", catalog = "postgres")
public class RuserroleEntity {
    private String id;
    private TroleEntity troleByRid;
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

        RuserroleEntity that = (RuserroleEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (troleByRid != null ? !troleByRid.equals(that.troleByRid) : that.troleByRid != null) return false;
        if (tuserByUid != null ? !tuserByUid.equals(that.tuserByUid) : that.tuserByUid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (troleByRid != null ? troleByRid.hashCode() : 0);
        result = 31 * result + (tuserByUid != null ? tuserByUid.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "rid", referencedColumnName = "rid")
    public TroleEntity getTroleByRid() {
        return troleByRid;
    }

    public void setTroleByRid(TroleEntity troleByRid) {
        this.troleByRid = troleByRid;
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
