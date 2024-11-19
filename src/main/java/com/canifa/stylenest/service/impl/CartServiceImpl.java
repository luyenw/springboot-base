package com.canifa.stylenest.service.impl;

import com.canifa.stylenest.entity.CartItem;
import com.canifa.stylenest.repository.CartRepository;
import com.canifa.stylenest.repository.OrderRepository;
import com.canifa.stylenest.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    @Override
    public List<CartItem> getItemsByUserId(Long userId) {
        return cartRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public List<CartItem> addItems(Long userId, Map<String, Long> quantityByModelId) {
        cartRepository.deleteAllByUserId(userId);
        return quantityByModelId.entrySet().stream()
                .map(row->  cartRepository.save(
                            CartItem.builder()
                                    .userId(userId)
                                    .modelId(row.getKey())
                                    .quantity(row.getValue())
                                    .build())
                ).toList();
    }

//    @Override
//    @Transactional
//    public Invoice checkout(Long userId, CheckoutRequest request) {
//        List<CartItem> items = getItemsByUserId(userId).stream().filter(CartItem::getIsLatest).toList();
//        Invoice invoice = invoiceRepository.save(Invoice.builder()
//                .name(request.getName())
//                .address(request.getAddress())
//                .tel(request.getTel())
//                .status(InvoiceStatus.ORDERED)
//                .value(LongStream.range(0, items.size()).map(i->items.get((int) i).getModel().getProduct().getPrice()*items.get((int) i).getQuantity()).sum())
//                .userId(userId)
//                .build());
//        getItemsByUserId(userId).stream().forEach(item->{
//            item.setInvoiceId(invoice.getId());
//            item.setIsLatest(false);
//        });
//        return invoice;
//    }
}
