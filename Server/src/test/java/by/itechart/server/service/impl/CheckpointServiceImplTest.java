package by.itechart.Server.service.impl;

import by.itechart.server.entity.Checkpoint;
import by.itechart.server.repository.CheckpointRepository;
import by.itechart.server.service.impl.CheckpointServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class CheckpointServiceImplTest {
    @Mock
    private CheckpointRepository checkpointRepository;
    @InjectMocks
    private CheckpointServiceImpl checkpointService=new CheckpointServiceImpl(checkpointRepository);

    private Checkpoint checkpoint;

    @Before
    public void setUp(){
        checkpoint=new Checkpoint();
        checkpoint.setId(1);
        checkpoint.setName("test");
        checkpoint.setDate(LocalDate.now());
        checkpoint.setLatitude("test");
        checkpoint.setLongitude("test");
    }

    @After
    public void tearDown(){
        checkpoint=null;
    }

    @Test
    public void save() {
    }

    @Test
    public void findAll() {
        Mockito.when(checkpointRepository.findAll()).thenReturn(Arrays.asList(checkpoint));
        assertEquals(Arrays.asList(checkpoint),checkpointService.findAll());
    }

    @Test
    public void findById() {
        Optional<Checkpoint> optinal= Optional.of(checkpoint);
        Mockito.when(checkpointRepository.findById(anyInt())).thenReturn(optinal);
        assertEquals(optinal,checkpointService.findById(anyInt()));
    }

    @Test
    public void deleteById() {
    }
}