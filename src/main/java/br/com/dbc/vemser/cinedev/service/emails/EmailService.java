package br.com.dbc.vemser.cinedev.service.emails;

import br.com.dbc.vemser.cinedev.dto.ClienteDTO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {
    private final freemarker.template.Configuration fmConfiguration;
    @Value("${spring.mail.username}")
    private String from;
    private static final String TO = "moises.noah@dbccompany.com.br";
    private final JavaMailSender emailSender;
    public void sendSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(TO);
        message.setSubject("Assunto");
        message.setText("Teste \n minha mensagem \n\nAtt,\nSistema.");
        emailSender.send(message);
    }
    public void sendWithAttachment() throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                true);
        helper.setFrom(from);
        helper.setTo(TO);
        helper.setSubject("Subject");
        helper.setText("Teste\n minha mensagem \n\nAtt,\nSistema.");
        File file1 = new File("imagem.jpg");
        FileSystemResource file
                = new FileSystemResource(file1);
        helper.addAttachment(file1.getName(), file);
        emailSender.send(message);
    }
    public void sendEmail(ClienteDTO clienteDTO, TipoEmails tipoEmails) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo("moises.noah@dbccompany.com.br");
//            mimeMessageHelper.setTo(clienteDTO.getEmail());
            mimeMessageHelper.setSubject(tipoEmails.getDescricao());
            mimeMessageHelper.setText(geContentFromTemplate(clienteDTO, tipoEmails), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
    public String geContentFromTemplate(ClienteDTO clienteDTO, TipoEmails tipoEmails) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", clienteDTO.getPrimeiroNome());
        dados.put("email", from);
        if (tipoEmails.equals(TipoEmails.CREATE)) {
            dados.put("texto1", "Estamos felizes em ter você em nosso sistema!");
            dados.put("texto2", "Seu cadastro foi realizado com sucesso, seu identificador é: " + clienteDTO.getIdCliente() + "!");
        } else if (tipoEmails.equals(TipoEmails.UPDATE)) {
            dados.put("texto1", "Você atualizou seus dados com sucesso! ");
            dados.put("texto2", "--------------------------------------");
        } else if (tipoEmails.equals(TipoEmails.DELETE)) {
            dados.put("texto1", "Que pena! Você perdeu o acesso ao nosso sistema!!");
            dados.put("texto2", "--------------------------------------");
        } else if (tipoEmails.equals(TipoEmails.ING_COMPRADO)) {
            dados.put("texto1", "A compra do seu ingresso foi realizada com sucesso!");
            dados.put("texto2", "Obrigado!");
        }
        Template template = fmConfiguration.getTemplate("email-template.html");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
}
