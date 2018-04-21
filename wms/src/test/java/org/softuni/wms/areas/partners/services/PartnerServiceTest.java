package org.softuni.wms.areas.partners.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.softuni.wms.areas.partners.entities.Partner;
import org.softuni.wms.areas.partners.models.binding.AddPartnerDto;
import org.softuni.wms.areas.partners.models.binding.EditPartnerDto;
import org.softuni.wms.areas.partners.models.view.PartnerViewDto;
import org.softuni.wms.areas.partners.repositories.PartnerDao;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PartnerServiceTest {

    @Mock
    private PartnerDao partnerDao;

    @InjectMocks
    private PartnerServiceImpl partnerServiceImpl;

    @Mock
    private Pageable pageable;

    private Partner testCustomer;
    private Partner testSupplier;
    private AddPartnerDto testCustomerDto;
    private AddPartnerDto testSupplierDto;

    @Before
    public void setUp() throws Exception {
        this.testCustomerDto = new AddPartnerDto();
        this.testCustomerDto.setName("TestCustomer");
        this.testCustomerDto.setVatNumber("1234567890");
        this.testCustomerDto.setAddress("Test Address");
        this.testCustomerDto.setPhoneNumber("1234567890");
        this.testCustomerDto.setCustomer(true);
        this.testCustomerDto.setSupplier(false);

        this.testSupplierDto = new AddPartnerDto();
        this.testSupplierDto.setName("TestSupplier");
        this.testSupplierDto.setVatNumber("1234567890");
        this.testSupplierDto.setAddress("Test Address");
        this.testSupplierDto.setPhoneNumber("1234567890");
        this.testSupplierDto.setCustomer(false);
        this.testSupplierDto.setSupplier(true);

        this.testCustomer = new Partner();
        this.testCustomer.setId("1");
        this.testCustomer.setName("TestCustomer");
        this.testCustomer.setVatNumber("1234567890");
        this.testCustomer.setAddress("Test Address");
        this.testCustomer.setPhoneNumber("1234567890");
        this.testCustomer.setCustomer(true);
        this.testCustomer.setSupplier(false);

        this.testSupplier = new Partner();
        this.testSupplier.setId("2");
        this.testSupplier.setName("TestSupplier");
        this.testSupplier.setVatNumber("1234567890");
        this.testSupplier.setAddress("Test Address");
        this.testSupplier.setPhoneNumber("1234567890");
        this.testSupplier.setCustomer(false);
        this.testSupplier.setSupplier(true);

        when(this.partnerDao.saveAndFlush(any())).thenAnswer(a -> a.getArgument(0));

        List<Partner> partners = List.of(this.testCustomer, this.testSupplier);

        when(this.partnerDao.findAll(pageable)).thenReturn(new PageImpl<Partner>(partners));
        when(this.partnerDao.getOne("1")).thenReturn(this.testCustomer);
    }

    @Test
    public void addPartner_WithValidPartner_ShouldNotReturnFalse() {
        boolean isCreated = this.partnerServiceImpl.addPartner(this.testCustomerDto);

        Assert.assertNotEquals("Partner was not created", false, isCreated);
    }

    @Test
    public void findAllByPage_ShouldReturnAllPartners(){

        this.partnerServiceImpl.addPartner(this.testCustomerDto);
        this.partnerServiceImpl.addPartner(this.testSupplierDto);

        Page<PartnerViewDto> allByPage = this.partnerServiceImpl.findAllByPage(this.pageable);

        Assert.assertEquals("Did not return all partners", 2,allByPage.getTotalElements() );
    }

    @Test
    public void getOne_ShouldMapCorrectly(){
        EditPartnerDto editPartnerDto = this.partnerServiceImpl.getOne("1");
        Assert.assertEquals(this.testCustomer.getId(),editPartnerDto.getId());
        Assert.assertEquals(this.testCustomer.getName(),editPartnerDto.getName());
        Assert.assertEquals(this.testCustomer.getVatNumber(),editPartnerDto.getVatNumber());
        Assert.assertEquals(this.testCustomer.getAddress(),editPartnerDto.getAddress());
        Assert.assertEquals(this.testCustomer.getPhoneNumber(),editPartnerDto.getPhoneNumber());
        Assert.assertEquals(this.testCustomer.getCustomer(),editPartnerDto.getCustomer());
        Assert.assertEquals(this.testCustomer.getSupplier(),editPartnerDto.getSupplier());
    }
}