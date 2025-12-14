package vvikobra.miit.skirental.services;

import com.example.skirentalcontracts.api.dto.PagedResponse;
import com.example.skirentalcontracts.api.dto.equipment.EquipmentRequest;
import com.example.skirentalcontracts.api.dto.equipment.EquipmentResponse;

public interface EquipmentService {
    PagedResponse<EquipmentResponse> getAvailableEquipment(EquipmentRequest request, int page, int size);
}
