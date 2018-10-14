package org.rdtif.zaxslackbot.interpreter;

import com.zaxsoft.zax.zmachine.ZCPU;
import com.zaxsoft.zax.zmachine.ZUserInterface;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class ZCpuFactoryTest {
    private final ZUserInterface userInterface = mock(ZUserInterface.class);
    private final ZCpuFactory factory = new ZCpuFactory();

    @Test
    void neverReturnNull() {
        ZCPU zcpu = factory.create(userInterface);

        assertThat(zcpu, notNullValue());
    }

    @Test
    void createWithProvidedUserInterface() {
        ZCPU zcpu = factory.create(userInterface);
        doThrow(IllegalArgumentException.class).when(userInterface).fatal(anyString());

        assertThrows(IllegalArgumentException.class, () -> zcpu.initialize(""));
    }
}
