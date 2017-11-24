package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tuserinfo", schema = "public", catalog = "postgres")
public class TuserinfoEntity {
    private String uiid;
    private String etc;
    private TuserEntity tusersByUid;

    @Id
    @GeneratedValue(generator = "autoid")
    @GenericGenerator(name = "autoid", strategy = "uuid")
    @Column(name = "uiid")
    public String getUiid() {
        return uiid;
    }

    public void setUiid(String uiid) {
        this.uiid = uiid;
    }

    @Basic
    @Column(name = "etc")
    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TuserinfoEntity that = (TuserinfoEntity) o;

        if (uiid != null ? !uiid.equals(that.uiid) : that.uiid != null) return false;
        if (etc != null ? !etc.equals(that.etc) : that.etc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uiid != null ? uiid.hashCode() : 0;
        result = 31 * result + (etc != null ? etc.hashCode() : 0);
        return result;
    }

    @OneToOne(mappedBy = "tuserinfoByUiid")
    public TuserEntity getTusersByUiid() {
        return tusersByUid;
    }

    public void setTusersByUiid(TuserEntity tusersByUid) {
        this.tusersByUid = tusersByUid;
    }
}
