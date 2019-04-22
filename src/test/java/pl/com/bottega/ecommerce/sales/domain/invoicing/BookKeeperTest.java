package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Test;
import org.mockito.Mockito;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BookKeeperTest {

    @Test
    public void requestingInvoiceWithOnePositionShouldReturnInvoiceWithOnePosition() {
        final int EXPECTED_VALUE = 1;
        TaxPolicy taxPolicy = Mockito.mock(TaxPolicy.class);

        Mockito.when(taxPolicy.calculateTax(Mockito.any(ProductType.class), Mockito.any(Money.class))).thenReturn(new Tax(Money.ZERO, "test"));

        BookKeeper bookKeeper = new BookKeeper(new InvoiceFactory());
        InvoiceRequest invoiceRequest = new InvoiceRequest(new ClientData(new Id("195018"),"Justyna"));
        ProductData productData = Mockito.mock(ProductData.class);

        Mockito.when(productData.getType()).thenReturn(ProductType.STANDARD);

        invoiceRequest.add(new RequestItem(productData, 1, Money.ZERO));
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

        assertThat(invoice.getItems().size(), is(EXPECTED_VALUE));
    }


}
