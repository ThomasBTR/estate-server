package com.estate.estateserver.mapper;

import com.estate.estateserver.auth.UserResponse;
import com.estate.estateserver.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface IUserMapper {

    IUserMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(IUserMapper.class);


    UserResponse toUserResponse(User user);
}
