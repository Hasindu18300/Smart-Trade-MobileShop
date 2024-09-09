package dto;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

public class Response_DTO implements Serializable {

    @Expose
    private boolean Success;
    
    @Expose
    private Object content;

    public Response_DTO() {
    }

    public Response_DTO(boolean Success, Object content) {
        this.Success = Success;
        this.content = content;
    }

    public boolean isSuccess() {
        return Success;
    }

    public Object getContent() {
        return content;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public void setContent(Object content) {
        this.content = content;
    }

}
