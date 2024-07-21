package com.rjb.hobby_tracker.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UserDTO {
    private String username;

    private String password;

    private String email;

    private String bio;
}
