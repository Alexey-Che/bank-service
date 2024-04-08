package org.example.bankservice.mapper;

import org.example.bankservice.domain.Email;
import org.example.bankservice.domain.PhoneNumber;
import org.example.bankservice.domain.User;
import org.example.bankservice.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface UserMapper {

    @Mapping(target = "phones", source = "phoneNumbers", qualifiedByName = "phones")
    @Mapping(target = "balance", source = "account.balance")
    @Mapping(target = "emails", source = "emails", qualifiedByName = "emails")
    UserDto doMap(User user);

    default List<UserDto> doMap(List<User> users) {
        return users.stream()
                .map(this::doMap)
                .toList();
    }

    @Named("phones")
    default List<String> getPhones(List<PhoneNumber> phoneNumbers) {
        return phoneNumbers.stream()
                .map(PhoneNumber::getPhone)
                .toList();
    }

    @Named("emails")
        default List<String> getEmails(List<Email> emails) {
        return emails.stream()
                .map(Email::getEmail)
                .toList();
    }
}
