package com.testing.logging.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class LoggingAspectTest {

    @Mock
    private Signature signature;

    @Test
    public void test_afterMethodInControllerClass() throws Throwable {
        //given
        LoggingAspect loggingAspect = new LoggingAspect();

        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        doReturn(new Object()).when(joinPoint).proceed();
        doReturn(signature).when(joinPoint).getSignature();
        Object[] args = {"test1", "test2"};
        doReturn(args).when(joinPoint).getArgs();

        //when
        Object o = loggingAspect.log(joinPoint);

        assertThat(o).isNotNull();
    }
}