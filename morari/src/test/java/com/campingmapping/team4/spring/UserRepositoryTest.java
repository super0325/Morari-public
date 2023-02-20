package com.campingmapping.team4.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = "classpath:test/data.sql") // sql 檔案放置的地方
@TestPropertySource(locations = "classpath:test/application.yml")// yml 檔案放置的地方
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // 在類中別的每個測試方法之前
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  // @Test
  // public void testFindByEmail() {
  //   Optional<UserProfiles> user = userRepository.findByEmail("jackEEIT56@gmail.com");
  //   userRepository.delete(user.get());
  //   Optional<UserProfiles> deletedUser = userRepository.findByEmail("jackEEIT56@gmail.com");
  //   assertThat(deletedUser).isNotPresent();  
  // }
  @Test
  public void testFindByEmail() {
        Optional<UserProfiles> user = userRepository.findByEmail("suc12345@gmail.com");
    assertThat(user).isPresent();
    assertThat(user.get().getEmail()).isEqualTo("suc12345@example.com");
  }

}