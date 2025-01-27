package org.anonymous.email.constants;

public enum AuthStatus {
    REQUESTED,   // 인증 요청됨
    VERIFIED,    // 인증 완료
    EXPIRED,     // 인증 만료
    FAILED       // 인증 실패
}
