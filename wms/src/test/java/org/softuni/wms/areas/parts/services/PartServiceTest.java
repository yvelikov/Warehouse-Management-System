package org.softuni.wms.areas.parts.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.softuni.wms.areas.partners.entities.Partner;
import org.softuni.wms.areas.partners.models.service.SupplierServiceDto;
import org.softuni.wms.areas.partners.services.PartnerServiceImpl;
import org.softuni.wms.areas.parts.entities.Part;
import org.softuni.wms.areas.parts.entities.enums.UnitOfMeasure;
import org.softuni.wms.areas.parts.models.binding.AddPartDto;
import org.softuni.wms.areas.parts.models.binding.EditPartDto;
import org.softuni.wms.areas.parts.models.binding.OperationPartDto;
import org.softuni.wms.areas.parts.models.binding.PartsOperationDto;
import org.softuni.wms.areas.parts.models.service.PartServiceDto;
import org.softuni.wms.areas.parts.repositories.PartDao;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PartServiceTest {

    private static final Double MARK_UP = 10d;
    private static final Double HUNDRED_PERCENT = 100d;
    private static final long QUANTITY = 3L;

    @Mock
    private PartDao partDao;

    @InjectMocks
    private PartServiceImpl partService;

    @Mock
    private PartnerServiceImpl partnerService;

    @Mock
    private Pageable pageable;

    private Part part;
    private AddPartDto addPartDto;
    private EditPartDto editPartDto;
    private PartsOperationDto partsOperationDto;
    private Partner testSupplier;
    private SupplierServiceDto supplierServiceDto;

    @Before
    public void setUp() throws Exception {
        this.supplierServiceDto = new SupplierServiceDto();
        this.supplierServiceDto.setId("2");
        this.supplierServiceDto.setName("TestSupplier");
        this.supplierServiceDto.setVatNumber("1234567890");
        this.supplierServiceDto.setAddress("Test Address");
        this.supplierServiceDto.setPhoneNumber("1234567890");

        this.testSupplier = new Partner();
        this.testSupplier.setId("2");
        this.testSupplier.setName("TestSupplier");
        this.testSupplier.setVatNumber("1234567890");
        this.testSupplier.setAddress("Test Address");
        this.testSupplier.setPhoneNumber("1234567890");
        this.testSupplier.setCustomer(false);
        this.testSupplier.setSupplier(true);

        this.addPartDto = new AddPartDto();
        this.addPartDto.setArticleCode("123456780");
        this.addPartDto.setName("Controller");
        this.addPartDto.setUnitOfMeasure(UnitOfMeasure.PIECE);
        this.addPartDto.setDeliveryPrice(BigDecimal.TEN);
        this.addPartDto.setListPrice(BigDecimal.valueOf(11));
        this.addPartDto.setMarkUp(MARK_UP);
        this.addPartDto.setSupplier(supplierServiceDto.getName());

        this.part = new Part();
        this.part.setId("1");
        this.part.setArticleCode("123456780");
        this.part.setName("Controller");
        this.part.setUnitOfMeasure(UnitOfMeasure.PIECE);
        this.part.setDeliveryPrice(BigDecimal.TEN);
        this.part.setListPrice(BigDecimal.valueOf(11));
        this.part.setMarkUp(MARK_UP/ HUNDRED_PERCENT);
        this.part.setQuantity(0L);
        this.part.setSupplier(this.testSupplier);

        this.editPartDto = new EditPartDto();
        this.editPartDto.setId("1");
        this.editPartDto.setArticleCode("087654321");
        this.editPartDto.setName("Edited Controller");
        this.editPartDto.setUnitOfMeasure(UnitOfMeasure.PIECE);
        this.editPartDto.setDeliveryPrice(BigDecimal.valueOf(20));
        this.editPartDto.setListPrice(BigDecimal.valueOf(22));
        this.editPartDto.setMarkUp(MARK_UP);
        this.editPartDto.setSupplier(supplierServiceDto.getName());

        this.partsOperationDto = new PartsOperationDto();
        OperationPartDto operationPartDto= new OperationPartDto();
        operationPartDto.setId("1");
        operationPartDto.setQuantity(QUANTITY);
        this.partsOperationDto.setParts(new OperationPartDto[1]);
        this.partsOperationDto.getParts()[0] = operationPartDto;

        when(this.partDao.saveAndFlush(any())).thenAnswer(a -> a.getArgument(0));

        when(this.partDao.getOne(this.part.getId())).thenReturn(this.part);

        when(this.partnerService.findSupplierByName(addPartDto.getSupplier())).thenReturn(this.supplierServiceDto);

    }

    @Test
    public void addPart_withValidAddPartDto_resultNotNull(){
        PartServiceDto partServiceDto = this.partService.addPart(this.addPartDto);

        Assert.assertNotNull("Part was not created", partServiceDto);
    }

   @Test
    public void addPart_withValidAddPartDto_resultIsMappedCorrectly(){
        PartServiceDto partServiceDto = this.partService.addPart(this.addPartDto);

        Assert.assertEquals("Part article code not mapped correctly", this.part.getArticleCode(), partServiceDto.getArticleCode());
        Assert.assertEquals("Part name not mapped correctly", this.part.getName(), partServiceDto.getName());
        Assert.assertEquals("Part delivery price mapped correctly", this.part.getDeliveryPrice(), partServiceDto.getDeliveryPrice());
        Assert.assertEquals("Part list price not mapped correctly", this.part.getListPrice(), partServiceDto.getListPrice());
        Assert.assertEquals("Part mark up not mapped correctly", this.part.getMarkUp(), partServiceDto.getMarkUp());
        Assert.assertEquals("Part quantity not mapped correctly", this.part.getQuantity(), partServiceDto.getQuantity());
        Assert.assertEquals("Part supplier not mapped correctly", this.part.getSupplier().getId(), partServiceDto.getSupplier().getId());
    }

    @Test
    public void addPart_withInvalidSupplierName_returnsNull(){
        this.addPartDto.setSupplier("Fake supplier");
        PartServiceDto partServiceDto = this.partService.addPart(this.addPartDto);
        Assert.assertNull("Part saved with invalid supplier", partServiceDto);
    }

    @Test
    public void getOne_mapsEditPartDtoCorrectly(){
        EditPartDto editPartDto = this.partService.getOne(this.part.getId());
        double markUp = this.part.getMarkUp() * HUNDRED_PERCENT;

        Assert.assertEquals("EditPartDto id not mapped correctly", this.part.getId(), editPartDto.getId());
        Assert.assertEquals("EditPartDto article code not mapped correctly", this.part.getArticleCode(), editPartDto.getArticleCode());
        Assert.assertEquals("EditPartDto name not mapped correctly", this.part.getName(), editPartDto.getName());
        Assert.assertEquals("EditPartDto delivery price not mapped correctly", this.part.getDeliveryPrice(), editPartDto.getDeliveryPrice());
        Assert.assertEquals("EditPartDto list price not mapped correctly", this.part.getListPrice(), editPartDto.getListPrice());
        Assert.assertEquals("EditPartDto mark up not mapped correctly", markUp, editPartDto.getMarkUp(),0.01);
        Assert.assertEquals("EditPartDto mark up not mapped correctly", this.part.getSupplier().getName(), editPartDto.getSupplier());
        Assert.assertEquals("EditPartDto unit of measure not mapped correctly", this.part.getUnitOfMeasure(), editPartDto.getUnitOfMeasure());
    }

    @Test
    public void editPart_withValidAddPartDto_resultIsMappedCorrectly(){
        PartServiceDto partServiceDto = this.partService.editPart(this.editPartDto);
        double markUp = this.editPartDto.getMarkUp() / HUNDRED_PERCENT;

        Assert.assertEquals("Part id not mapped correctly", this.editPartDto.getId(), partServiceDto.getId());
        Assert.assertEquals("Part article code not mapped correctly", this.editPartDto.getArticleCode(), partServiceDto.getArticleCode());
        Assert.assertEquals("Part name not mapped correctly", this.editPartDto.getName(), partServiceDto.getName());
        Assert.assertEquals("Part delivery price mapped correctly", this.editPartDto.getDeliveryPrice(), partServiceDto.getDeliveryPrice());
        Assert.assertEquals("Part list price not mapped correctly", this.editPartDto.getListPrice(), partServiceDto.getListPrice());
        Assert.assertEquals("Part mark up not mapped correctly", markUp, partServiceDto.getMarkUp(),0.01);
        Assert.assertEquals("Part supplier not mapped correctly", this.editPartDto.getSupplier(), partServiceDto.getSupplier().getName());
    }

    @Test
    public void editPart_withInvalidSupplierName_returnsNull(){
        this.editPartDto.setSupplier("Fake supplier");
        PartServiceDto partServiceDto = this.partService.editPart(this.editPartDto);
        Assert.assertNull("Part saved with invalid supplier", partServiceDto);
    }

    @Test
    public void deliver_withValidPartOperationDto_updatesPartQuantity(){
        List<PartServiceDto> deliveredParts = this.partService.deliver(this.partsOperationDto);
        Assert.assertEquals("Part quantity not updated correctly on delivery", QUANTITY, deliveredParts.get(0).getQuantity().longValue());
    }

    @Test
    public void issue_withValidPartOperationDto_updatesPartQuantity(){
        this.part.setQuantity(QUANTITY);
        List<PartServiceDto> deliveredParts = this.partService.issue(this.partsOperationDto);
        Assert.assertEquals("Part quantity not updated correctly on issue", 0L, deliveredParts.get(0).getQuantity().longValue());
    }
}