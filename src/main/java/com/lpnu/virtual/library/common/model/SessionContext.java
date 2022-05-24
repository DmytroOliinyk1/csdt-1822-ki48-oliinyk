package com.lpnu.virtual.library.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionContext {
    private boolean isAuthorized;
    private boolean isAdmin;

}
