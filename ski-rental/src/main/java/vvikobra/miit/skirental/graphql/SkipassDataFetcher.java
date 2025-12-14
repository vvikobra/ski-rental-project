package vvikobra.miit.skirental.graphql;

import com.example.skirentalcontracts.api.dto.skipass.SkipassResponse;
import com.netflix.graphql.dgs.*;
import org.springframework.beans.factory.annotation.Autowired;
import vvikobra.miit.skirental.services.SkipassService;

import java.util.UUID;

@DgsComponent
public class SkipassDataFetcher {

    private final SkipassService skipassService;

    @Autowired
    public SkipassDataFetcher(SkipassService skipassService) {
        this.skipassService = skipassService;
    }

    @DgsQuery
    public SkipassResponse skipassById(@InputArgument("id") UUID id) {
        return skipassService.getSkipass(id);
    }

    @DgsMutation
    public SkipassResponse useSkipass(@InputArgument("id") UUID id) {
        return skipassService.useLift(id);
    }
}
