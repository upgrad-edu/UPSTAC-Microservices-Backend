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

@ExtendWith(MockitoExtension.class)
class DateParserTest {


    @Test
    public void when_getDateFromString_called_with__invalid_dateString_expect_exception_to_be_thrown(){


        AppException appException = assertThrows(AppException.class,()->{
            DateParser.getDateFromString("halo32633-68-32783");
        });

        assertThat(appException.getMessage(),containsString("Invalid Date String"));
    }
    @Test
    public void when_asLocalDate_called_with__empty_input_expect_exception_to_be_thrown(){


        AppException appException = assertThrows(AppException.class,()->{
            DateParser.asLocalDate(null);
        });

        assertThat(appException.getMessage(),containsString("Invalid Input"));
    }

    @Test
    public void when_asDate_called_with__empty_input_expect_exception_to_be_thrown(){


        AppException appException = assertThrows(AppException.class,()->{
            DateParser.asDate(null);
        });

        assertThat(appException.getMessage(),containsString("Invalid Input"));
    }

}