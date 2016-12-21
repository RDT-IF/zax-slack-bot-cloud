package org.rdtif.zaxslackbot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;

public class ListGamesActionTest {
    private final GameRepository repository = mock(GameRepository.class);
    private final ListGamesAction action = new ListGamesAction(repository);

    @Test
    public void neverReturnNull() {
        String response = action.execute(null);

        assertThat(response, notNullValue());
    }

    @Test
    public void returnFileListOne() {
        when(repository.fileNames()).thenReturn(Arrays.asList("file1"));
        String response = action.execute(null);

        assertThat(response, equalTo("file1."));
    }

    @Test
    public void returnFileListTwo() {
        when(repository.fileNames()).thenReturn(Arrays.asList("file1", "file2"));
        String response = action.execute(null);

        assertThat(response, equalTo("file1 and file2."));
    }

    @Test
    public void returnFileListThreeOrMore() {
        when(repository.fileNames()).thenReturn(Arrays.asList("file1", "file2", "file3"));
        String response = action.execute(null);

        assertThat(response, equalTo("file1, file2, and file3."));
    }
}
