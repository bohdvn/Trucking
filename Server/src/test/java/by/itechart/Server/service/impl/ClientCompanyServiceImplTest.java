package by.itechart.Server.service.impl;

import by.itechart.Server.entity.ClientCompany;
import by.itechart.Server.repository.ClientCompanyRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class ClientCompanyServiceImplTest {
    @Mock
    private ClientCompanyRepository clientCompanyRepository;
    @InjectMocks
    private ClientCompanyServiceImpl clientCompanyService=new ClientCompanyServiceImpl(clientCompanyRepository);

    private ClientCompany clientCompany;

    @Before
    public void setUp() throws Exception {
        clientCompany=new ClientCompany();
        clientCompany.setId(1);
        clientCompany.setName("test");
    }

    @After
    public void tearDown() throws Exception {
        clientCompany=null;
    }

    @Test
    public void save() {
    }

    @Test
    public void findAll() {
//        Mockito.when(clientCompanyRepository.findAll()).thenReturn(Arrays.asList(clientCompany));
//        assertEquals(Arrays.asList(clientCompany),clientCompanyService.findAll());
    }

    @Test
    public void findById() {
        Optional<ClientCompany> optional=Optional.of(clientCompany);
        Mockito.when(clientCompanyRepository.findById(anyInt())).thenReturn(optional);
        assertEquals(optional,clientCompanyService.findById(anyInt()));
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteById() {
    }
}