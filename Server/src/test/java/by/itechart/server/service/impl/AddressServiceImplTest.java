package by.itechart.server.service.impl;

import by.itechart.server.entity.Address;
import by.itechart.server.repository.AddressRepository;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplTest {
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private AddressServiceImpl addressService=new AddressServiceImpl(addressRepository);

    private Address address;

    @Before
    public void setUp(){
        address=new Address();
        address.setId(1);
        address.setCity("Minsk");
        address.setStreet("Street");
        address.setBuilding(1);
        address.setFlat(1);
        address.setLongitude("test");
        address.setLatitude("test");
    }

    @Test
    public void save() {
    }

    @Test
    public void findAll() {
        Mockito.when(addressRepository.findAll()).thenReturn(Arrays.asList(address));
        assertEquals(Arrays.asList(address),addressService.findAll());
    }

    @Test
    public void findById() {
        Optional<Address> optional=Optional.of(address);
        Mockito.when(addressRepository.findById(anyInt())).thenReturn(optional);
        assertEquals(address,addressService.findById(1));
    }

    @Test
    public void deleteById() {
    }

    @After
    public void tearDown(){
        address=null;
    }
}