package com.tania.service;

import java.util.List;
import java.util.Optional;

import com.tania.model.Seller;
import com.tania.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport( SellerReport sellerReport);

}
