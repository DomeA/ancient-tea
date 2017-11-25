package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.impl;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.*;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.repository.*;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TUserServiceImpl implements TUserService {
    @Autowired
    TUserRepository tUserRepository;
    @Autowired
    TRoleRepository tRoleRepository;
    @Autowired
    TResourceRepository tResourceRepository;
    @Autowired
    RUserRoleRepository rUserRoleRepository;
    @Autowired
    RUserResourceRepository rUserResourceRepository;
    @Autowired
    RRoleResourceRepository rRoleResourceRepository;


    @Override
    public TuserEntity login(String param, String pwd) {
        return tUserRepository.findByNameOrEmailOrPhone(param,pwd);
    }

    @Override
    public TuserEntity findUserOne(String id) {
        return tUserRepository.findOne(id);
    }

    @Override
    public TroleEntity findRoleOne(String id) {
        return tRoleRepository.findOne(id);
    }

    @Override
    public TresourceEntity findResourceOne(String id) {
        return tResourceRepository.findOne(id);
    }

    @Override
    public TuserEntity findUserByName(String name) {
        return tUserRepository.findByName(name);
    }

    @Override
    public TroleEntity findRoleByName(String name) {
        return tRoleRepository.findByName(name);
    }

    @Override
    public TresourceEntity findResourceByName(String name) {
        return tResourceRepository.findByName(name);
    }

    @Override
    public void save(TuserEntity entity) {
        tUserRepository.save(entity);
    }

    @Override
    public void save(TroleEntity troleEntity) {
        tRoleRepository.save(troleEntity);
    }

    @Override
    public void save(TresourceEntity tresourceEntity) {
        tResourceRepository.save(tresourceEntity);
    }

    @Override
    public TuserEntity findByNameOrEmailOrPhone(String param) {
        return tUserRepository.findByNameOrEmailOrPhone(param);
    }

    @Override
    public List<String> findRoleName(TuserEntity entity) {
        List<String> names=new ArrayList<>();
        List<RuserroleEntity> ruserroleEntities = rUserRoleRepository.findByTuserByUid(entity);
        for (RuserroleEntity rr: ruserroleEntities) {
            //tRoleRepository.findOne(rr.getTroleByRid().getRid()).getName();
            String name=rr.getTroleByRid().getName();
            names.add(name);
        }
        return names;
    }

    @Override
    public Boolean createUser(String name, String pwd,String email, String phone) {
        if(tUserRepository.findByNameOrEmailOrPhone(name)!=null||
                tUserRepository.findByNameOrEmailOrPhone(email)!=null||
                tUserRepository.findByNameOrEmailOrPhone(phone)!=null){
            return false;
        }
        TuserEntity tuserEntity=new TuserEntity();
        tuserEntity.setEmail(email);
        tuserEntity.setName(name);
        tuserEntity.setPwd(pwd);
        tuserEntity.setPhone(phone);
        save(tuserEntity);
        return true;
    }

    @Override
    public Boolean createRole(String name) {
        if(tRoleRepository.findByName(name)!=null){
            return false;
        }
        TroleEntity troleEntity=new TroleEntity();
        troleEntity.setName(name);
        save(troleEntity);
        return true;
    }

    @Override
    public Boolean createResource(String name) {
        if(tResourceRepository.findByName(name)!=null){
            return false;
        }
        TresourceEntity tresourceEntity=new TresourceEntity();
        tresourceEntity.setName(name);
        save(tresourceEntity);
        return true;
    }

    @Override
    public void allocationUserRole(TuserEntity tuserEntity, TroleEntity troleEntity) {
        RuserroleEntity ruserroleEntity=new RuserroleEntity();
        ruserroleEntity.setTuserByUid(tuserEntity);
        ruserroleEntity.setTroleByRid(troleEntity);
        rUserRoleRepository.save(ruserroleEntity);
    }

    @Override
    public void allocationUserResource(TuserEntity tuserEntity, TresourceEntity tresourceEntity) {
        RuserresourceEntity ruserresourceEntity=new RuserresourceEntity();
        ruserresourceEntity.setTresourceByReid(tresourceEntity);
        ruserresourceEntity.setTuserByUid(tuserEntity);
        rUserResourceRepository.save(ruserresourceEntity);
    }

    @Override
    public void allocationRoleResource(TroleEntity troleEntity, TresourceEntity tresourceEntity) {
        RroleresourceEntity rroleresourceEntity=new RroleresourceEntity();
        rroleresourceEntity.setTresourceByReid(tresourceEntity);
        rroleresourceEntity.setTroleByRid(troleEntity);
        rRoleResourceRepository.save(rroleresourceEntity);
    }
}
