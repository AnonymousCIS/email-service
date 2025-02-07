package org.anonymous.email.services;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.anonymous.email.controllers.RequestEmail;
import org.attoparser.dom.Text;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Lazy
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final LogUpdateService logService;

    /**
     *
     * @param form
     * @param tpl
     * @param tplData
     * @return
     */
    public boolean sendEmail(RequestEmail form, String tpl, Map<String, Object> tplData){
        try {
            Context context = new Context();
            tplData = Objects.requireNonNullElseGet(tplData, HashMap::new);

            List<String> to = form.getTo();
            List<String> cc = form.getCc();
            List<String> bcc = form.getBcc();
            String subject = form.getSubject();
            String content = form.getContent();
            List<MultipartFile> files = form.getFiles();

            tplData.put("to", to);
            tplData.put("cc", cc);
            tplData.put("bcc", bcc);
            tplData.put("subject", subject);
            tplData.put("content", content);

            context.setVariables(tplData);

            String html = templateEngine.process("email/" + tpl, context);

            boolean isFileAttached = files != null && !files.isEmpty();

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            System.out.println("To : " + form.getTo());
            helper.setTo(form.getTo().toArray(String[]::new));

            if (cc != null && !cc.isEmpty()) {
                helper.setCc(cc.toArray(String[]::new));
            }

            if (bcc != null && !bcc.isEmpty()) {
                helper.setBcc(bcc.toArray(String[]::new));
            }
            helper.setSubject(subject);
            helper.setText(html, true);
            if (isFileAttached) {
                for (MultipartFile file : files){
                    helper.addAttachment(file.getOriginalFilename(), file);
                }

            }
            javaMailSender.send(message);
            
            logService.logStatus(form); // 추가

            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean sendEmail(RequestEmail form, String tpl){
        return sendEmail(form, tpl, null);
    }
    public boolean sendEmail(String to, String subject, String content){
        RequestEmail form = new RequestEmail();
        form.setTo(List.of(to));
        form.setSubject(subject);
        form.setContent(content);
        logService.logStatus(form);
        return sendEmail(form,"general");
    }
}