package com.edu.ulab.app.repository;

import com.edu.ulab.app.config.SystemJpaTest;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import static com.vladmihalcea.sql.SQLStatementCountValidator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * Тесты репозитория {@link UserRepository}.
 */
@SystemJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        SQLStatementCountValidator.reset();
    }

    //create
    @DisplayName("Сохранить пользователя без книги. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void savePersonCheckAssertDmlCount() {
        //Given
        Person person = new Person();
        person.setAge(111);
        person.setTitle("Anonimus");
        person.setFullName("Charly chaplin");

        //When
        Person result = userRepository.save(person);

        //Then
        assertThat(result.getAge()).isEqualTo(111);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }


    //create 2
    @DisplayName("Сохранить пользователя без книги. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void savePersonsCheckAssertDmlCount() {
        //Given
        Person person = new Person();
        person.setAge(111);
        person.setTitle("Anonimus");
        person.setFullName("Charly chaplin");

        Person person2 = new Person();
        person2.setAge(21);
        person2.setTitle("reader");
        person2.setFullName("Alex Pirs");

        //When
        Person result = userRepository.save(person);
        Person result2 = userRepository.save(person2);

        //Then
        assertThat(result.getAge()).isEqualTo(111);
        assertThat(result2.getAge()).isEqualTo(21);
        assertSelectCount(2);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    // update
    @DisplayName("Изменить пользователя. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void updatePersonCheckAssertDmlCount() {
        //Given
        Person person = new Person();
        person.setId(1001L);
        person.setAge(31);
        person.setTitle("reader");
        person.setFullName("Ed van der Meer");

        //When
        Person result = userRepository.save(person);

        //Then
        assertThat(result.getAge()).isEqualTo(31);
        assertThat(result.getFullName()).isEqualTo("Ed van der Meer");
        assertThat(result.getId()).isEqualTo(1001L);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
    // get
    @DisplayName("Выдать пользователя. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getPersonCheckAssertDmlCount() {
        //Given
        Long personId = 1001L;
        //When
        Person result = userRepository.findById(personId).orElse(new Person());

        //Then
        assertThat(result.getAge()).isEqualTo(55);
        assertThat(result.getFullName()).isEqualTo("default uer");
        assertThat(result.getId()).isEqualTo(1001L);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
    // delete
    @DisplayName("Удалить юзера. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void deletePersonCheckAssertDmlCount() {
        //Given
        Long personId = 1001L;
        //When
        userRepository.deleteById(personId);
        Person result = userRepository.findById(personId).orElse(new Person());
        //Then
        assertThat(result.getAge()).isEqualTo(null);
        assertThat(result.getFullName()).isEqualTo(null);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
    // * failed
    @DisplayName("Ошибка при выдаче пользователя. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void failedGetPersonCheckAssertDmlCount() {
        //Given
        Long personId = 1000L;
        //When
        Person result = userRepository.findById(personId).orElse(null);

        //Then
        assertThatThrownBy(result::getAge)
                .isInstanceOf(NullPointerException.class);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
    // * failed
}
