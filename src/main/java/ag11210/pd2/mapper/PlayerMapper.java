package ag11210.pd2.mapper;

import ag11210.pd2.dto.PlayerInfoDto;
import ag11210.pd2.model.PlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    PlayerEntity dtoToEntity(PlayerInfoDto playerInfoDto);
}
