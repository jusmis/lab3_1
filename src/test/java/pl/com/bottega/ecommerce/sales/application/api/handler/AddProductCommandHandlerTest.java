package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.junit.Test;
import org.mockito.Mockito;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.Client;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.sharedkernel.Money;
import pl.com.bottega.ecommerce.system.application.SystemContext;

public class AddProductCommandHandlerTest {

    @Test
    public void reservingOneProductShouldCallAddMethodOnce(){
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        SuggestionService suggestionService = Mockito.mock(SuggestionService.class);
        ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
        SystemContext systemContext = Mockito.mock(SystemContext.class);
        Product product = new Product(Id.generate(), Money.ZERO, "test", ProductType.STANDARD);
        Reservation reservation = Mockito.mock(Reservation.class);

        Mockito.when(productRepository.load(Mockito.any())).thenReturn(product);
        Mockito.when(clientRepository.load(Mockito.any())).thenReturn(new Client());
        Mockito.when(reservationRepository.load(Mockito.any())).thenReturn(reservation);
        Mockito.when(systemContext.getSystemUser()).thenCallRealMethod();

        AddProductCommandHandler addProductCommandHandler = new AddProductCommandHandler(reservationRepository, productRepository, suggestionService, clientRepository, systemContext);
        addProductCommandHandler.handle(new AddProductCommand(Id.generate(), product.getId(), Mockito.anyInt()));

        Mockito.verify(reservation, Mockito.times(1)).add(Mockito.eq(product), Mockito.anyInt());
    }
}
