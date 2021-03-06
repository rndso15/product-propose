package com.product.propose.domain.account.web.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoResponse {

    // Account
    private String email;
    private String nickName;

    // UserProfile
    private String userName;
}
