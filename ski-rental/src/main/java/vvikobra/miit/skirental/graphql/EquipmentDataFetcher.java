package vvikobra.miit.skirental.graphql;

import com.example.skirentalcontracts.api.dto.PagedResponse;
import com.example.skirentalcontracts.api.dto.equipment.EquipmentRequest;
import com.example.skirentalcontracts.api.dto.equipment.EquipmentResponse;
import com.netflix.graphql.dgs.*;
import org.springframework.beans.factory.annotation.Autowired;
import vvikobra.miit.skirental.services.EquipmentService;

import java.time.LocalDateTime;
import java.util.Map;

@DgsComponent
public class EquipmentDataFetcher {

    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentDataFetcher(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @DgsQuery
    public PagedResponse<EquipmentResponse> equipment(
            @InputArgument("filter") Map<String, Object> filter,
            @InputArgument Integer page,
            @InputArgument Integer size
    ) {
        LocalDateTime timeFrom = filter.get("timeFrom") != null
                ? LocalDateTime.parse((String) filter.get("timeFrom"))
                : null;
        LocalDateTime timeTo = filter.get("timeTo") != null
                ? LocalDateTime.parse((String) filter.get("timeTo"))
                : null;
        String type = (String) filter.get("type");
        Double height = filter.get("height") != null
                ? ((Number) filter.get("height")).doubleValue()
                : null;
        Double weight = filter.get("weight") != null
                ? ((Number) filter.get("weight")).doubleValue()
                : null;
        String skillLevel = (String) filter.get("skillLevel");
        Integer shoeSize = filter.get("shoeSize") != null
                ? ((Number) filter.get("shoeSize")).intValue()
                : null;

        EquipmentRequest request = new EquipmentRequest(timeFrom, timeTo, type, height, weight, skillLevel, shoeSize);

        int currentPage = page != null ? page : 0;
        int pageSize = size != null ? size : 10;

        return equipmentService.getAvailableEquipment(request, currentPage, pageSize);
    }
}
