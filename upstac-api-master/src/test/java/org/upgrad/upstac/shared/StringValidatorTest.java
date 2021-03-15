package org.upgrad.upstac.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.upgrad.upstac.exception.AppException;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.upgrad.upstac.shared.StringValidator.isNotEmptyOrNull;

@ExtendWith(MockitoExtension.class)
class StringValidatorTest {

    @Test
    public void test_isNotEmptyOrNull(){


        String validString ="SOME_VALUE";
        String nullString =null;
        StringValidator validObject = new StringValidator();
        StringValidator nullObject = null;
        Integer validInteger = 2111;
        Integer nullInteger = null;

        assertThat(isNotEmptyOrNull(validString),is(true));
        assertThat(isNotEmptyOrNull(nullString),is(false));
        assertThat(isNotEmptyOrNull(validObject),is(true));
        assertThat(isNotEmptyOrNull(nullObject),is(false));
        assertThat(isNotEmptyOrNull(validInteger),is(true));
        assertThat(isNotEmptyOrNull(nullInteger),is(false));
    }

}