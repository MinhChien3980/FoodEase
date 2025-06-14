package com.foodease.myapp.controller;

import com.foodease.myapp.service.AddressService;
import com.foodease.myapp.service.dto.request.AddressRequest;
import com.foodease.myapp.service.dto.response.AddressResponse;
import com.foodease.myapp.service.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@Tag(name = "Address", description = "Operations related to addresses")
@CrossOrigin(origins = "http://localhost:5173")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AddressController {
    AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/all")
    public ApiResponse<List<AddressResponse>> getAllAddresses() {
        List<AddressResponse> addresses = addressService.getAllAddresses();
        return ApiResponse.<List<AddressResponse>>builder()
                .code(200)
                .data(addresses)
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<AddressResponse>> getAddressesByUserId(@Valid @PathVariable Long userId) {
        List<AddressResponse> addresses = addressService.getAddressesByUserId(userId);
        return ApiResponse.<List<AddressResponse>>builder()
                .code(200)
                .data(addresses)
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<AddressResponse> createAddress(@Valid @RequestBody AddressRequest addressRequest) {
        AddressResponse address = addressService.createAddress(addressRequest);
        if (address == null) {
            return ApiResponse.<AddressResponse>builder()
                    .code(400)
                    .message("Failed to create address")
                    .build();
        }
        return ApiResponse.<AddressResponse>builder()
                .code(201)
                .data(address)
                .message("Address created successfully")
                .build();
    }

    @PutMapping("/update")
    public ApiResponse<AddressResponse> updateAddress(
            @RequestParam Long id,
            @Valid @RequestBody AddressRequest addressRequest
    ) {
        AddressResponse updatedAddress = addressService.updateAddress(id, addressRequest);
        if (updatedAddress == null) {
            return ApiResponse.<AddressResponse>builder()
                    .code(404)
                    .message("Address not found")
                    .build();
        }
        return ApiResponse.<AddressResponse>builder()
                .code(200)
                .data(updatedAddress)
                .message("Address updated successfully")
                .build();
    }

    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteAddress(@RequestParam Long id) {
        boolean deleted = addressService.deleteAddress(id);
        if (!deleted) {
            return ApiResponse.<Void>builder()
                    .code(404)
                    .message("Address not found")
                    .build();
        }
        return ApiResponse.<Void>builder()
                .code(204)
                .message("Address deleted successfully")
                .build();
    }
} 