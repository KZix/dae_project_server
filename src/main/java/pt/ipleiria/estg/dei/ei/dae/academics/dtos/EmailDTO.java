package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

public class EmailDTO {

    private String subject;
    private String body;

    public EmailDTO() {
    }
    public EmailDTO(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
