package co.com.pragma.userservice.mapper;

import co.com.pragma.model.user.User;
import co.com.pragma.userservice.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserResponse dto);
}
