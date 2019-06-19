package by.itechart.server.service.impl;

import by.itechart.server.entity.Invoice;
import by.itechart.server.repository.InvoiceRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceImplTest {
    @Mock
    private InvoiceRepository invoiceRepository;
    @InjectMocks
    //   privatceImpl invoiceService=new InvoiceServiceImpl(invoiceRepository);

    private Invoice invoice;

    //    @Beforee InvoiceServi
    public void setUp() {
        invoice=new Invoice();
        invoice.setId(1);
        invoice.setStatus(Invoice.Status.CHECKED);
        invoice.setDateOfIssue(LocalDate.now());
        invoice.setDateOfCheck(LocalDate.now());
        invoice.setNumber("test");
    }

    @After
    public void tearDown() {
        invoice=null;
    }

    @Test
    public void save() {
    }

    @Test
    public void findAll() {
        Mockito.when(invoiceRepository.findAll()).thenReturn(Arrays.asList(invoice));
        //   assertEquals(Arrays.asList(invoice),invoiceService.findAll());
    }

    @Test
    public void findById() {
        Optional<Invoice> optional=Optional.of(invoice);
        Mockito.when(invoiceRepository.findById(anyInt())).thenReturn(optional);
        //    assertEquals(optional,invoiceService.findById(anyInt()));
    }

    @Test
    public void deleteById() {
    }
}