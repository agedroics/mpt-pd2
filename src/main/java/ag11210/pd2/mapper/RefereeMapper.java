package ag11210.pd2.mapper;

import ag11210.pd2.dto.RefereeDto;
import ag11210.pd2.model.RefereeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RefereeMapper {

    @Mapping(target = "id", ignore = true)
    RefereeEntity dtoToEntity(RefereeDto refereeDto);
}
