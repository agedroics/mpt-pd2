package ag11210.pd2.mapper;

import ag11210.pd2.dto.GameDto;
import ag11210.pd2.model.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "players", ignore = true)
    @Mapping(target = "starters", ignore = true)
    @Mapping(target = "referee", ignore = true)
    @Mapping(target = "assistantReferees", ignore = true)
    @Mapping(target = "fouls", ignore = true)
    @Mapping(target = "goals", ignore = true)
    @Mapping(target = "substitutions", ignore = true)
    GameEntity dtoToEntity(GameDto gameDto);
}
