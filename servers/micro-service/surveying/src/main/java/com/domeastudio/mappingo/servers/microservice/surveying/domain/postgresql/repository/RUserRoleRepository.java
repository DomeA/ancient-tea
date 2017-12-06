package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.repository;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.RuserroleEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TroleEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TuserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RUserRoleRepository extends JpaRepository<RuserroleEntity, String> {
    List<RuserroleEntity> findByTuserByUid(TuserEntity tuserEntity);

    RuserroleEntity findByTuserByUidAndTroleByRid(TuserEntity tuserEntity, TroleEntity troleEntity);
}
