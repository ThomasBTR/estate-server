package com.estate.estateserver.mappers;

import com.estate.estateserver.models.entities.User;
import com.estate.estateserver.models.responses.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    //    @Mapping(target = "updated_at", source = "updatedAt", dateFormat = "yyyy/MM/dd")
//    @Mapping(target = "created_at", source = "createdAt", dateFormat = "yyyy/MM/dd")
    UserResponse userToUserResponse(User user);
}
