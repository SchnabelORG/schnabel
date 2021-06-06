package com.schnabel.schnabel.procurement.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.procurement.dto.OfferDTO;
import com.schnabel.schnabel.procurement.model.Offer;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Offer Service interface
 */
public interface IOfferService extends IJpaService<Offer, Long>
{
    PagedModel<OfferDTO> findBySupplier(Pageable pageable, Long id);
    boolean createOffer(int price, LocalDate dateOfDelivery, long orderId);
    PagedModel<OfferDTO> findByOrder(Pageable pageable, Long id);
    boolean updateOffer(Long id, int price, LocalDate dateOfDelivery);
    Optional<OfferDTO> getDTO(Long id);
    PagedModel<OfferDTO> getAllDTO(Pageable pageable);
    boolean acceptOffer(Long offerId, String email, Pageable pageable);
    boolean checkEmptyOrderOfferList(Long orderId, Pageable pageable);
}

