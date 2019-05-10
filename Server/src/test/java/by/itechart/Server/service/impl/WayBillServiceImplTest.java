package by.itechart.Server.service.impl;

import by.itechart.Server.entity.WayBill;
import by.itechart.Server.repository.WayBillRepository;
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
public class WayBillServiceImplTest {
    @Mock
    private WayBillRepository wayBillRepository;
    @InjectMocks
    private WayBillServiceImpl wayBillService=new WayBillServiceImpl(wayBillRepository);

    private WayBill wayBill;

    @Before
    public void setUp() {
        wayBill=new WayBill();
        wayBill.setId(1);
        wayBill.setStatus(WayBill.Status.STARTED);
        wayBill.setDateFrom(LocalDate.now());
        wayBill.setDateTo(LocalDate.now());
    }

    @After
    public void tearDown() {
        wayBill=null;
    }

    @Test
    public void save() {
    }

    @Test
    public void findAll() {
        Mockito.when(wayBillRepository.findAll()).thenReturn(Arrays.asList(wayBill));
        assertEquals(Arrays.asList(wayBill),wayBillService.findAll());
    }

    @Test
    public void findById() {
        Optional<WayBill> optional=Optional.of(wayBill);
        Mockito.when(wayBillRepository.findById(anyInt())).thenReturn(optional);
        assertEquals(optional,wayBillService.findById(anyInt()));
    }

    @Test
    public void deleteById() {
    }
}