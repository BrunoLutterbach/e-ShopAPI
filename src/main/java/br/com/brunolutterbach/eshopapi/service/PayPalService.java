package br.com.brunolutterbach.eshopapi.service;

import br.com.brunolutterbach.eshopapi.model.pedido.Pedido;
import br.com.brunolutterbach.eshopapi.repository.PedidoRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayPalService {

    final APIContext apiContext;
    final PedidoRepository pedidoRepository;

    public PayPalService(APIContext apiContext, PedidoRepository pedidoRepository) {
        this.apiContext = apiContext;
        this.pedidoRepository = pedidoRepository;
    }

    public String createOrder(BigDecimal valorTotal, String returnUrl, String cancelUrl, Pedido pedido) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency("BRL");
        amount.setTotal(valorTotal.toString());

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Pedido " + pedido.getId());
        transaction.setCustom(pedido.getId().toString());

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("SALE");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(returnUrl);
        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment = payment.create(apiContext);

        pedido.setPaymentId(createdPayment.getId());
        pedidoRepository.save(pedido);

        return createdPayment.getLinks().stream()
                .filter(link -> link.getRel().equalsIgnoreCase("approval_url"))
                .findFirst()
                .orElseThrow(() -> new PayPalRESTException("Erro ao criar pagamento"))
                .getHref();
    }
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecution);
    }
}


