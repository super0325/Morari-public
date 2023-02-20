package com.campingmapping.team4.spring.utils.config;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.campingmapping.team4.spring.t401member.model.dao.repository.RoleRepository;
import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
// import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRoleRepository;
import com.campingmapping.team4.spring.t401member.model.entity.Role;
import com.campingmapping.team4.spring.t401member.model.entity.UserDetail;
import com.campingmapping.team4.spring.t401member.model.entity.UserName;
import com.campingmapping.team4.spring.t401member.model.entity.UserPrivacy;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t424camp.model.dao.repository.CityRepository;
import com.campingmapping.team4.spring.t424camp.model.dao.repository.TagRepository;
import com.campingmapping.team4.spring.t424camp.model.entity.City;
import com.campingmapping.team4.spring.t424camp.model.entity.Tag;

import jakarta.transaction.Transactional;

@Component
// 啟動時自動執行
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    // @Autowired
    // private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TagRepository tagRepository;
    
    @Autowired
    private CityRepository cityRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 查看有無權限並創建
        if (alreadySetup)
            return;
        List<String> roles = Arrays.asList(
                "SUPERADMIN", "ADMIN", "CAMP", "SHOP", "FORUM", "MALL", "TEAM", "USER","COMPANY");
        roles.forEach(r -> createRoleIfNotFound(r));

        List<String> tags = Arrays.asList( "大草原", "夜景", "親子娛樂", "雲海", "泡湯", "螢火蟲" );
        tags.forEach(t->createTagIfNotFound(t));

        List<String> citys = Arrays.asList( "新北", "桃園", "新竹", "苗栗", "南投", "宜蘭", "台東" );
        citys.forEach(c->createCityIfNotFound(c));

        // 檢查有無存在生成超級管理員
        Role adminRole = roleRepository.findByName("SUPERADMIN").get();

        Optional<UserProfiles> userOptional = userRepository.findByEmail(MyConstants.SUPER_ADMIN_NAME);
        UserProfiles userProfiles;
        // Name
        UserName userName = UserName.builder()
                .firstname("camp")
                .lastname("morari")
                .build();
        // Privacy
        UserPrivacy userPrivacy = UserPrivacy.builder()
                .address("morari")
                .phone("0912345678")
                .build();
        // Detail
        UserDetail userDetail = UserDetail.builder()
                .nickname("燚~超-級=管=理-員~燚")
                .exp(999999L)
                .level(999999)
                .point(99999999L)
                .gender(0)
                .subscribed(false)
                .shot("https://storage.googleapis.com/morariphoto/adminshot")
                .about("想幹嘛就幹嘛")
                .registerdata(new Date())
                .build();
        try {
            if (userOptional.isPresent()) {
                userProfiles = userOptional.get();
                userProfiles.setUserdetail(userDetail);
                userProfiles.setUsernames(userName);
                userProfiles.setUserprivacy(userPrivacy);
                userProfiles.setEmail(MyConstants.SUPER_ADMIN_NAME);
                userProfiles.setPassword(passwordEncoder.encode(MyConstants.SUPER_ADMIN_PASSWORD));
                userProfiles.getRoles().clear();
                userProfiles.getRoles().add(adminRole);
                userProfiles.setAccountnonexpired(true);
                userProfiles.setAccountnonlocked(true);
                userProfiles.setIscredentialsnonexpired(true);
                userProfiles.setIsenabled(true);
                userRepository.save(userProfiles);
            } else {
                // Role adminRole = roleRepository.findByName("SUPERADMIN").get();
                userDetail.setRegisterdata(new Date());
                userPrivacy.setBirthday(new Date());
                userProfiles = UserProfiles.builder()
                        .email(MyConstants.SUPER_ADMIN_NAME)
                        .password(passwordEncoder.encode(MyConstants.SUPER_ADMIN_PASSWORD))
                        .uid(UUID.randomUUID())
                        .userdetail(userDetail)
                        .usernames(userName)
                        .userprivacy(userPrivacy)
                        .accountnonexpired(true)
                        .iscredentialsnonexpired(true)
                        .isenabled(true)
                        .accountnonlocked(true)
                        .build();
                userProfiles.getRoles().add(adminRole);
                userRepository.save(userProfiles);



            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        alreadySetup = true;
    }

    @Transactional
    public Role createRoleIfNotFound(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> roleRepository.save(Role.builder().name(name).build()));
    }
    @Transactional
    public Tag createTagIfNotFound(String tagName) {
        return tagRepository.findByTagName(tagName)
                .orElseGet(() -> tagRepository.save(new Tag(tagName)));
    }

    @Transactional
    public City createCityIfNotFound(String cityName) {
        return cityRepository.findByCityName(cityName)
                .orElseGet(() -> cityRepository.save(new City(cityName)));
    }
}
