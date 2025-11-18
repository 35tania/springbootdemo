package com.tania.service.impl;

import com.tania.config.JwtProvider;
import com.tania.domain.AccountStatus;
import com.tania.domain.USER_ROLE;
import com.tania.exception.SellerException;
import com.tania.exception.UserException;
import com.tania.model.Address;
import com.tania.model.Seller;
import com.tania.model.User;
import com.tania.repository.AddressRepository;
import com.tania.repository.SellerRepository;
import com.tania.repository.UserRepository;
import com.tania.request.LoginRequest;
import com.tania.response.AuthResponse;
import com.tania.service.SellerService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;


    @Override
    public Seller getSellerProfile(String jwt) throws SellerException {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws SellerException {
        Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());
        if (sellerExist != null) {
            throw new SellerException("Seller already exists used different email");
        }

        Address savedAddress = addressRepository.save(seller.getPickupAddress());


        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPickupAddress(savedAddress);
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setGstin(seller.getGstin());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setMobile(seller.getMobile());
        newSeller.setHasGSTIN(seller.isHasGSTIN());
        newSeller.setPanNumber(seller.getPanNumber());
        newSeller.setAadhaarNumber(seller.getAadhaarNumber());
        newSeller.setConfirmPassword(passwordEncoder.encode(seller.getConfirmPassword()));
        newSeller.setEmailVerified(seller.isEmailVerified());
        

        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        System.out.println(newSeller);
        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        if (optionalSeller.isPresent()) {
            return optionalSeller.get();
        }
        throw new SellerException("Seller not found");
    }

    @Override
    public Seller getSellerByEmail(String email) throws SellerException {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller != null) {
            return seller;
        }
        throw new SellerException("Seller not found");
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws SellerException {
        Seller existingSeller = sellerRepository.findById(id)
                .orElseThrow(() ->
                        new SellerException("Seller not found with id " + id));


        if (seller.getSellerName() != null) {
            existingSeller.setSellerName(seller.getSellerName());
        }
        if (seller.getMobile() != null) {
            existingSeller.setMobile(seller.getMobile());
        }
        if (seller.getEmail() != null) {
            existingSeller.setEmail(seller.getEmail());
        }

        if (seller.getBusinessDetails() != null
                && seller.getBusinessDetails().getBusinessName() != null
        ) {

            existingSeller.getBusinessDetails().setBusinessName(
                    seller.getBusinessDetails().getBusinessName()
            );
        }

        if (seller.getBankDetails() != null
                && seller.getBankDetails().getAccountHolderName() != null
                && seller.getBankDetails().getIfscCode() != null
                && seller.getBankDetails().getAccountNumber() != null
        ) {

            existingSeller.getBankDetails().setAccountHolderName(
                    seller.getBankDetails().getAccountHolderName()
            );
            existingSeller.getBankDetails().setAccountNumber(
                    seller.getBankDetails().getAccountNumber()
            );
            existingSeller.getBankDetails().setIfscCode(
                    seller.getBankDetails().getIfscCode()
            );
        }
        if (seller.getPickupAddress() != null
                && seller.getPickupAddress().getAddress() != null
                && seller.getPickupAddress().getMobile() != null
                && seller.getPickupAddress().getCity() != null
                && seller.getPickupAddress().getState() != null
        ) {
            existingSeller.getPickupAddress()
                    .setAddress(seller.getPickupAddress().getAddress());
            existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
            existingSeller.getPickupAddress().setMobile(seller.getPickupAddress().getMobile());
            existingSeller.getPickupAddress().setPinCode(seller.getPickupAddress().getPinCode());
        }
        if (seller.getGstin() != null) {
            existingSeller.setGstin(seller.getGstin());
        }


        return sellerRepository.save(existingSeller);

    }

    @Override
    public void deleteSeller(Long id) throws SellerException {
        if (sellerRepository.existsById(id)) {
            sellerRepository.deleteById(id);
        } else {
            throw new SellerException("Seller not found with id " + id);
        }
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws SellerException {
        Seller seller = this.getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws SellerException {
        Seller seller = this.getSellerById(sellerId);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
    
    @Override
    public AuthResponse login(LoginRequest request) throws UserException {

        // ======================
        //      USER LOGIN
        // ======================
        User user = userRepository.findByEmail(request.getEmail());
        if (user != null) {

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new UserException("Invalid password!");
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    request.getEmail(), null
            );

            String token = jwtProvider.generateToken(auth);

            AuthResponse res = new AuthResponse();
            res.setMessage("User login successful");
            res.setJwt(token);
            res.setRole(user.getRole());   // ✅ correct
            res.setStatus(true);

            return res;
        }

        // ======================
        //      SELLER LOGIN
        // ======================
        Seller seller = sellerRepository.findByEmail(request.getEmail());
        if (seller != null) {

            if (!passwordEncoder.matches(request.getPassword(), seller.getPassword())) {
                throw new UserException("Invalid password!");
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    request.getEmail(), null
            );

            String token = jwtProvider.generateToken(auth);

            AuthResponse res = new AuthResponse();
            res.setMessage("Seller login successful");  // optional change
            res.setJwt(token);
            res.setRole(seller.getRole());   // ✅ FIXED — use seller role
            res.setStatus(true);

            return res;
        }

        throw new UserException("No account found with this email!");
    }

}

