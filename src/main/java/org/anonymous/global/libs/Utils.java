package org.anonymous.global.libs;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.anonymous.global.entities.CodeValue;
import org.anonymous.global.repositories.CodeValueRepository;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

@Lazy
@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;
    private final MessageSource messageSource;
    private final DiscoveryClient discoveryClient;
    private final CodeValueRepository codeValueRepository;

    /**
     * 메서지 코드로 조회된 문구
     *
     * @param code
     * @return
     */
    public String getMessage(String code) {
        Locale lo = request.getLocale(); // 사용자 요청 헤더(Accept-Language)

        return messageSource.getMessage(code, null, lo);
    }

    public List<String> getMessages(String[] codes) {

        return Arrays.stream(codes).map(c -> {
            try {
                return getMessage(c);
            } catch (Exception e) {
                return "";
            }
        }).filter(s -> !s.isBlank()).toList();

    }

    /**
     * REST 커맨드 객체 검증 실패시에 에러 코드를 가지고 메세지 추출
     *
     * @param errors
     * @return
     */
    public Map<String, List<String>> getErrorMessages(Errors errors) {
        ResourceBundleMessageSource ms = (ResourceBundleMessageSource) messageSource;


            // 필드별 에러코드 - getFieldErrors()
            // Collectors.toMap
            Map<String, List<String>> messages = errors.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, f -> getMessages(f.getCodes()), (v1, v2) -> v2));

            // 글로벌 에러코드 - getGlobalErrors()
            List<String> gMessages = errors.getGlobalErrors()
                    .stream()
                    .flatMap(o -> getMessages(o.getCodes()).stream())
                    .toList();
            // 글로벌 에러코드 필드 - global
            if (!gMessages.isEmpty()) {
                messages.put("global", gMessages);
            }

            return messages;
    }


    /**
     * 유레카 서버 인스턴스 주소 검색
     *
     *      spring.profiles.active : dev - localhost로 되어 있는 주소를 반환
     *          - 예) member-service : 최대 2가지만 존재, 1 - 실 서비스 도메인 주소, 2. localhost ...
     * @param serviceId
     * @param url
     * @return
     */
    public String  serviceUrl(String serviceId, String url) {
        try {
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
            String profile = System.getenv("spring.profiles.active");
            boolean isDev = StringUtils.hasText(profile) && profile.contains("dev");
            String serviceUrl = null;
            for (ServiceInstance instance : instances) {
                String uri = instance.getUri().toString();
                if (isDev && uri.contains("localhost")) {
                    serviceUrl = uri;
                } else if (!isDev && !uri.contains("localhost")) {
                    serviceUrl = uri;
                }
            }

            if (StringUtils.hasText(serviceUrl)) {
                return serviceUrl + url;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 요청 헤더 : Authorizaion: Bearer ...
     * @return
     */
    public String getAuthToken() {
        String auth = request.getHeader("Authorization"); // 요청 헤더에 Authorization 가져옴

        return StringUtils.hasText(auth) ? auth.substring(7).trim() : null; // auth가 null이 아니면 문자열에서 7번째 인덱스 이후의 부분을 잘라낸 후, 그 앞뒤의 공백을 제거 null일시 null
    }

//    /**
//     * Code - Value 값 저장
//     *
//     * @param code
//     * @param value
//     */
//    public <T> void saveValue(String code, T value) { //제네릭으로 확인
//        CodeValue codeValue = new CodeValue(); // 생성
//        codeValue.setCode(code); // 제네릭으로 들어온 코드에 맞게 셋해줌
//        codeValue.setValue(value); // 제네릭으로 들어온 자료형에 맞게 셋해줌
//        codeValueRepository.save(codeValue); // codeValueRepository에 저장
//    }
    /**
     * 전체 주소
     *
     * @param url
     * @return
     */
    public String getUrl(String url) {
        int port = request.getServerPort();
        String _port = port == 80 || port == 443 ? "" : ":" + port;
        return String.format("%s://%s%s%s%s", request.getScheme(), request.getServerName(), _port, request.getContextPath(), url);
    }

    public String getUserHash(){
        String userKey = "" + Objects.hash("userHash");

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(userKey)) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
    public void saveValue(String code, Object value){
        CodeValue item = new CodeValue();

        item.setCode(code);
        item.setValue(value);

        codeValueRepository.save(item);
    }

    public <T> T getValue(String code) {
        CodeValue data = codeValueRepository.findByCode(code);

        return data == null ? null : (T)data.getValue();
    }
}
