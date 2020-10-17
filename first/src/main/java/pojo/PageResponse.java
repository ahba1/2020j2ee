package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse implements Serializable {

    private List<NormalUser> users;
    private int totalSize;

}
